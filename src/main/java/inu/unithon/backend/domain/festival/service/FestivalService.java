package inu.unithon.backend.domain.festival.service;

import inu.unithon.backend.domain.festival.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import inu.unithon.backend.domain.festival.service.FestivalServiceInterface;

import java.net.URI;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
@RequiredArgsConstructor
public class FestivalService implements FestivalServiceInterface{

    private static final Logger logger = LoggerFactory.getLogger(FestivalService.class);

    @Value("${tourapi.service-key}")
    private String encodedServiceKey;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public FestivalResponseDto getFestivalList(String lang, String numOfRows, String pageNo, String eventStartDate, String areaCode) {
        try {
            String serviceName = getServiceName(lang);
            String baseUrl = "http://apis.data.go.kr/B551011/";
            String servicePath = serviceName + "/searchFestival1";

            String url = baseUrl + servicePath
                    + "?serviceKey=" + encodedServiceKey
                    + "&MobileApp=UnithonApp"
                    + "&MobileOS=ETC"
                    + "&arrange=A"
                    + "&listYN=Y"
                    + "&_type=json"
                    + "&numOfRows=" + numOfRows
                    + "&pageNo=" + pageNo
                    + "&eventStartDate=" + eventStartDate;

            if (areaCode != null && !areaCode.isEmpty()) {
                url += "&areaCode=" + areaCode;
            }

            logger.info("📡 도커 요청 URL: {}", url);

            URI uri = new URI(url);
            String jsonString = restTemplate.getForObject(uri, String.class);

            return objectMapper.readValue(jsonString, FestivalResponseDto.class);

        } catch (Exception e) {
            logger.error("축제 리스트 조회 중 오류 발생: ", e);
            throw new RuntimeException("축제 리스트 조회 실패", e);
        }
    }

    public FestivalResponseDto getFestivalInfo(String lang, String contentId) {
        try {
            String serviceName = getServiceName(lang);
            String baseUrl = "http://apis.data.go.kr/B551011/";
            String servicePath = serviceName + "/detailCommon1";

            String url = baseUrl + servicePath
                    + "?serviceKey=" + encodedServiceKey
                    + "&MobileApp=UnithonApp"
                    + "&MobileOS=ETC"
                    + "&contentId=" + contentId
                    + "&_type=json"
                    + "&defaultYN=Y"
                    + "&firstImageYN=Y"
                    + "&addrinfoYN=Y"
                    + "&mapinfoYN=Y"
                    + "&overviewYN=Y";

            logger.info("📡 도커 요청 URL: {}", url);

            URI uri = new URI(url);
            String jsonString = restTemplate.getForObject(uri, String.class);

            return objectMapper.readValue(jsonString, FestivalResponseDto.class);

        } catch (Exception e) {
            logger.error("축제 상세 정보 조회 중 오류 발생: ", e);
            throw new RuntimeException("축제 상세 정보 조회 실패", e);
        }
    }

    public FestivalResponseDto getSearchFestival(String lang, String keyword) {
        try {
            String serviceName = getServiceName(lang);
            String baseUrl = "http://apis.data.go.kr/B551011/";
            String servicePath = serviceName + "/searchKeyword1";

            String url = baseUrl + servicePath
                    + "?serviceKey=" + encodedServiceKey
                    + "&MobileApp=UnithonApp"
                    + "&MobileOS=ETC"
                    + "&arrange=A"
                    + "&listYN=Y"
                    + "&_type=json"
                    + "&numOfRows=10"
                    + "&pageNo=1"
                    + "&contentTypeId=" + getContentid(lang)
                    + "&keyword=" + keyword;

            logger.info("📡 도커 요청 URL: {}", url);

            URI uri = new URI(url);
            String jsonString = restTemplate.getForObject(uri, String.class);

            return objectMapper.readValue(jsonString, FestivalResponseDto.class);

        } catch (Exception e) {
            logger.error("축제 검색 조회 중 오류 발생: ", e);
            throw new RuntimeException("축제 검색 조회 실패", e);
        }
    }

    public FestivalIntroResponseDto getFestivalDetailIntro(String lang, String contentId, String contentTypeId) {
        try {
            String serviceName = getServiceName(lang);
            String baseUrl = "http://apis.data.go.kr/B551011/";
            String servicePath = serviceName + "/detailIntro1";

            String url = baseUrl + servicePath
                    + "?serviceKey=" + encodedServiceKey
                    + "&MobileApp=UnithonApp"
                    + "&MobileOS=ETC"
                    + "&contentId=" + contentId
                    + "&contentTypeId=" + contentTypeId
                    + "&_type=json";

            logger.info("📡 도커 요청 URL: {}", url);

            URI uri = new URI(url);
            String jsonString = restTemplate.getForObject(uri, String.class);

            return objectMapper.readValue(jsonString, FestivalIntroResponseDto.class);

        } catch (Exception e) {
            logger.error("축제 소개 정보 조회 중 오류 발생: ", e);
            throw new RuntimeException("축제 소개 정보 조회 실패", e);
        }
    }

    public FestivalInfoResponseDto getFestivalDetailInfo(String lang, String contentId, String contentTypeId) {
        try {
            String serviceName = getServiceName(lang);
            String baseUrl = "http://apis.data.go.kr/B551011/";
            String servicePath = serviceName + "/detailInfo1";

            String url = baseUrl + servicePath
                    + "?serviceKey=" + encodedServiceKey
                    + "&MobileApp=UnithonApp"
                    + "&MobileOS=ETC"
                    + "&contentId=" + contentId
                    + "&contentTypeId=" + contentTypeId
                    + "&_type=json";

            logger.info("📡 도커 요청 URL: {}", url);

            URI uri = new URI(url);
            String jsonString = restTemplate.getForObject(uri, String.class);

            return objectMapper.readValue(jsonString, FestivalInfoResponseDto.class);

        } catch (Exception e) {
            logger.error("축제 추가 정보 조회 중 오류 발생: ", e);
            throw new RuntimeException("축제 추가 정보 조회 실패", e);
        }
    }

    private String getContentid(String lang) {
        return switch (lang.toLowerCase()) {
            case "kor" -> "15";
            case "jpn" -> "85";
            case "chn" -> "85";
            case "eng" -> "85";
            case "fra" -> "85";
            case "ger" -> "85";
            case "rus" -> "85";
            case "spa" -> "85";
            default -> "15";
        };
    }

    private String getServiceName(String lang) {
        return switch (lang.toLowerCase()) {
            case "kor" -> "KorService1";
            case "jpn" -> "JpnService1";
            case "chn" -> "ChnService1";
            case "eng" -> "EngService1";
            case "fra" -> "FraService1";
            case "ger" -> "GerService1";
            default -> "KorService1";
        };
    }
}
