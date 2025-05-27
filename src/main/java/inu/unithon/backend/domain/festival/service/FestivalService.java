package inu.unithon.backend.domain.festival.service;

import inu.unithon.backend.domain.festival.dto.FestivalResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class FestivalService {

    private static final Logger logger = LoggerFactory.getLogger(FestivalService.class);

    @Value("${tourapi.service-key}")
    private String rawServiceKey; // 반드시 인코딩되지 않은 원본 키

    private final RestTemplate restTemplate;

    public FestivalResponseDto getFestivalList(String lang, String numOfRows, String pageNo, String eventStartDate, String areaCode) {
        String serviceName = getServiceName(lang);
        String baseUrl = "http://apis.data.go.kr/B551011" + serviceName + "/searchFestival1";

        Map<String, String> params = new LinkedHashMap<>();
        params.put("serviceKey", rawServiceKey);
        params.put("MobileApp", "UnithonApp");
        params.put("MobileOS", "ETC");
        params.put("arrange", "A");
        params.put("listYN", "Y");
        params.put("_type", "json");
        params.put("numOfRows", numOfRows);
        params.put("pageNo", pageNo);
        params.put("eventStartDate", eventStartDate);
        params.put("areaCode", areaCode);

        String finalUrl = buildTourApiUrl(baseUrl, params);
        logger.info("📡 [Info] 최종 URL: {}", finalUrl);

        String rawResponse = restTemplate.getForObject(finalUrl, String.class);
        logger.info("📜 [Info] Raw API 응답: {}", rawResponse);

        return restTemplate.getForObject(finalUrl, FestivalResponseDto.class);

    }

    public FestivalResponseDto getFestivalInfo(String lang, String contentId) {
        String serviceName = getServiceName(lang);
        String baseUrl = "http://apis.data.go.kr/B551011" + serviceName + "/detailCommon1";

        Map<String, String> params = new LinkedHashMap<>();
        params.put("serviceKey", rawServiceKey);
        params.put("MobileApp", "UnithonApp");
        params.put("MobileOS", "ETC");
        params.put("contentId", contentId);
        params.put("_type", "json");
        params.put("defaultYN", "Y");
        params.put("firstImageYN", "Y");
        params.put("addrinfoYN", "Y");
        params.put("mapinfoYN", "Y");
        params.put("overviewYN", "Y");

        String finalUrl = buildTourApiUrl(baseUrl, params);
        logger.info("📡 [Info] 최종 URL: {}", finalUrl);

        String rawResponse = restTemplate.getForObject(finalUrl, String.class);
        logger.info("📜 [Info] Raw API 응답: {}", rawResponse);

        return restTemplate.getForObject(finalUrl, FestivalResponseDto.class);
    }

    private String getServiceName(String lang) {
        return switch (lang.toLowerCase()) {
            case "kor" -> "/KorService1";
            case "jpn" -> "/JpnService1";
            case "chn" -> "/ChnService1";
            case "eng" -> "/EngService1";
            case "fra" -> "/FraService1";
            case "ger" -> "/GerService1";
            default -> "/KorService1";
        };
    }

    private String buildTourApiUrl(String baseUrl, Map<String, String> params) {
        StringBuilder urlBuilder = new StringBuilder(baseUrl);
        boolean isFirst = true;

        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (entry.getValue() == null || entry.getValue().isEmpty()) continue;

            String key = URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8);
            String value = URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8);

            urlBuilder.append(isFirst ? "?" : "&")
                    .append(key)
                    .append("=")
                    .append(value);
            isFirst = false;
        }

        return urlBuilder.toString();
    }
}
