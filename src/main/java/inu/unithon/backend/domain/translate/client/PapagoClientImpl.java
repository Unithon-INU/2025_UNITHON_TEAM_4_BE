package inu.unithon.backend.domain.translate.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Slf4j
@Component
@RequiredArgsConstructor
public class PapagoClientImpl implements PapagoClient {

  private final HttpClient client = HttpClient.newHttpClient();
  private final ObjectMapper objectMapper = new ObjectMapper();

  @Value("${ncp.api.papago.translate-url}")
  private String BASE_URL;

  @Value("${ncp.api.papago.client-id}")
  private String clientId;

  @Value("${ncp.api.papago.client-secret}")
  private String clientSecret;

  private static final int MAX_RETRIES = 5;

  @Override
  public String translate(String text, String source, String target) {

    log.info("Papago ID: {}, Secret: {}", clientId, clientSecret);

    if (text == null || text.isBlank()) return "";

    try {
      String jsonBody = objectMapper.writeValueAsString(
        new PapagoRequest(source, target, text)
      );

      HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create(BASE_URL))
        .header("Content-Type", "application/json")
        .header("X-NCP-APIGW-API-KEY-ID", clientId)
        .header("X-NCP-APIGW-API-KEY", clientSecret)
        .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
        .build();

      // 요청 + 백오프 처리
      String responseBody = executeWithRetry(request);

      if (responseBody == null) {
        log.warn("Papago 요청 실패 — 원문 반환");
        return text;
      }

      // 응답 파싱
      JsonNode root = objectMapper.readTree(responseBody);
      String translated = root.path("message").path("result").path("translatedText").asText();

      return translated != null && !translated.isBlank() ? translated : text;

    } catch (Exception e) {
      log.error("[Papago] 요청 중 예외 발생: {}", e.getMessage(), e);
      return text;
    }
  }

  /**
   * 429 Too Many Requests 발생 시 exponential back off 로 재시도
   */
  private String executeWithRetry(HttpRequest request) {
    for (int attempt = 1; attempt <= MAX_RETRIES; attempt++) {
      try {
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        int status = response.statusCode();

        if (status == 200) return response.body();

        if (status == 429) {
          long delay = (long) Math.pow(2, attempt) * 1000;
          log.warn("[Papago] 429 Too Many Requests — {}회차 {}ms 대기 후 재시도", attempt, delay);
          Thread.sleep(delay);
          continue;
        }

        if (status == 400) {
          JsonNode err = objectMapper.readTree(response.body());
          String code = err.path("errorCode").asText();
          handlePapagoError(code);
        }

        log.error("[Papago] 요청 실패 (HTTP {}): {}", status, response.body());
        return null;

      } catch (Exception e) {
        log.error("[Papago] {}회차 요청 실패: {}", attempt, e.getMessage());
      }
    }

    log.error("[Papago] 최대 {}회 재시도 후 실패", MAX_RETRIES);
    return null;
  }

  /**
   * Papago 오류 코드별 세부 로그
   */
  private void handlePapagoError(String errorCode) {
    switch (errorCode) {
      case "N2MT01" -> log.warn("source parameter 누락");
      case "N2MT02" -> log.warn("지원하지 않는 source 언어");
      case "N2MT03" -> log.warn("target parameter 누락");
      case "N2MT04" -> log.warn("지원하지 않는 target 언어");
      case "N2MT05" -> log.warn("source와 target이 동일함");
      case "N2MT06" -> log.warn("source→target 번역기 없음");
      case "N2MT07" -> log.warn("text parameter 누락");
      case "N2MT08" -> log.warn("text 길이 초과 (너무 긴 문장)");
      case "N2MT09" -> log.warn("언어 감지 실패");
      case "N2MT10" -> log.warn("유효하지 않은 glossary key");
      case "N2MT99" -> log.warn("Papago 서버 내부 오류");
      default -> log.warn("알 수 없는 Papago 오류 코드: {}", errorCode);
    }
  }

  private record PapagoRequest(String source, String target, String text) {}
}
