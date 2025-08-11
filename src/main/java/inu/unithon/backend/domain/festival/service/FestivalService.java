package inu.unithon.backend.domain.festival.service;

import inu.unithon.backend.domain.festival.dto.*;
import inu.unithon.backend.domain.festival.entity.Festival;
import inu.unithon.backend.domain.festival.repository.FestivalRepository;
import jakarta.transaction.Transactional;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import inu.unithon.backend.domain.festival.service.FestivalServiceInterface;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import inu.unithon.backend.global.exception.CustomException;
import inu.unithon.backend.global.exception.ErrorCode;
import inu.unithon.backend.domain.festival.dto.FestivalDto;

import java.net.URI;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.time.LocalDateTime;
import java.util.Set;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
@RequiredArgsConstructor
public class FestivalService implements FestivalServiceInterface{

    private static final Logger logger = LoggerFactory.getLogger(FestivalService.class);
    private final FestivalRepository festivalRepository;

    @Value("${tourapi.service-key}")
    private String encodedServiceKey;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public FestivalResponseDto getFestivalList(String lang, String numOfRows, String pageNo, String eventStartDate, String areaCode, String eventEndDate) {
        try {
            String serviceName = getServiceName(lang);
            String baseUrl = "http://apis.data.go.kr/B551011/";
            String servicePath = serviceName + "/searchFestival2";

            String url = baseUrl + servicePath
                    + "?serviceKey=" + encodedServiceKey
                    + "&MobileApp=UnithonApp"
                    + "&MobileOS=ETC"
                    + "&arrange=A"
                    + "&_type=json"
                    + "&numOfRows=" + numOfRows
                    + "&pageNo=" + pageNo
                    + "&eventStartDate=" + eventStartDate;
            if (eventEndDate != null && !eventEndDate.isEmpty()) {
                url += "&eventEndDate=" + eventEndDate;
            }

            if (areaCode != null && !areaCode.isEmpty()) {
                url += "&areaCode=" + areaCode;
            }

            logger.info("📡 도커 요청 URL: {}", url);

            URI uri = new URI(url);
            String jsonString = restTemplate.getForObject(uri, String.class);

            return objectMapper.readValue(jsonString, FestivalResponseDto.class);

        } catch (Exception e) {
            logger.error("Festival List error : ", e);
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    public FestivalResponseDto getFestivalInfo(String lang, String contentId) {
        try {
            String serviceName = getServiceName(lang);
            String baseUrl = "http://apis.data.go.kr/B551011/";
            String servicePath = serviceName + "/detailCommon2";

            String url = baseUrl + servicePath
                    + "?serviceKey=" + encodedServiceKey
                    + "&MobileApp=UnithonApp"
                    + "&MobileOS=ETC"
                    + "&contentId=" + contentId
                    + "&_type=json";

            logger.info("📡 도커 요청 URL: {}", url);

            URI uri = new URI(url);
            String jsonString = restTemplate.getForObject(uri, String.class);

            return objectMapper.readValue(jsonString, FestivalResponseDto.class);

        } catch (Exception e) {
            logger.error("Festival info error : ", e);
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    public FestivalResponseDto getSearchFestival(String lang, String keyword, String numOfRows, String pageNo) {
        try {
            String serviceName = getServiceName(lang);
            String baseUrl = "http://apis.data.go.kr/B551011/";
            String servicePath = serviceName + "/searchKeyword2";
            if ("KorService1".equals(serviceName) || "JpnService1".equals(serviceName) || "ChsService1".equals(serviceName)) {
                keyword = URLEncoder.encode(keyword, StandardCharsets.UTF_8);
            }

            String url = baseUrl + servicePath
                    + "?serviceKey=" + encodedServiceKey
                    + "&MobileApp=UnithonApp"
                    + "&MobileOS=ETC"
                    + "&arrange=A"
                    + "&_type=json"
                    + "&numOfRows=" + numOfRows
                    + "&pageNo=" + pageNo
                    + "&contentTypeId=" + getContentid(lang)
                    + "&keyword=" + keyword;

            logger.info("📡 도커 요청 URL: {}", url);

            URI uri = new URI(url);
            String jsonString = restTemplate.getForObject(uri, String.class);

            return objectMapper.readValue(jsonString, FestivalResponseDto.class);

        } catch (Exception e) {
            logger.error("Festival Search error : ", e);
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    public FestivalIntroResponseDto getFestivalDetailIntro(String lang, String contentId, String contentTypeId) {
        try {
            String serviceName = getServiceName(lang);
            String baseUrl = "http://apis.data.go.kr/B551011/";
            String servicePath = serviceName + "/detailIntro2";

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
            logger.error("Festival intro error : ", e);
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    public FestivalInfoResponseDto getFestivalDetailInfo(String lang, String contentId, String contentTypeId) {
        try {
            String serviceName = getServiceName(lang);
            String baseUrl = "http://apis.data.go.kr/B551011/";
            String servicePath = serviceName + "/detailInfo2";

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
            logger.error("Festival Detail info error : ", e);
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }
    public FestivalResponseDto getFestivalLocationFood(String lang, String MapX, String MapY, String NumOfRows, String PageNo, String radius) {
        try {
            String serviceName = getServiceName(lang);
            String baseUrl = "http://apis.data.go.kr/B551011/";
            String servicePath = serviceName + "/locationBasedList2";

            String url = baseUrl + servicePath
                    + "?serviceKey=" + encodedServiceKey
                    + "&MobileApp=UnithonApp"
                    + "&radius=" + radius
                    + "&MobileOS=ETC"
                    + "&_type=json"
                    + "&numOfRows=" + NumOfRows
                    + "&pageNo=" + PageNo
                    + "&mapX=" + MapX
                    + "&mapY=" + MapY
                    + "&radius=1000"
                    + "&contentTypeId=" + getFoodid(lang);

            logger.info("📡 도커 요청 URL: {}", url);

            URI uri = new URI(url);
            String jsonString = restTemplate.getForObject(uri, String.class);

            return objectMapper.readValue(jsonString, FestivalResponseDto.class);

        } catch (Exception e) {
            logger.error("Location Food List error : ", e);
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    private Festival listToEntity(FestivalDto dto) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDateTime start = null;
        LocalDateTime end = null;

        try {
            if(dto.getEventenddate() != null && !dto.getEventenddate().isBlank()){
                start = LocalDateTime.parse(dto.getEventenddate(), formatter).withHour(0).withMinute(0);
            } if(dto.getEventstartdate() != null && !dto.getEventstartdate().isBlank()){
                end = LocalDateTime.parse(dto.getEventstartdate(), formatter).withHour(0).withMinute(0);
            }
        } catch (Exception e) {
            throw new CustomException(ErrorCode.DATE_PARSE_ERROR);
        }
        return Festival.builder()
                .contentId(dto.getContentid())
                .title(dto.getTitle())
                .startDate(start)
                .endDate(end)
                .imageUrl(dto.getFirstimage())
                .build();
    }

    @Transactional
    public void saveFestivalList(List<FestivalDto> dtoList) {
        List<Long> incomingContentIds = dtoList.stream()
                .map(FestivalDto::getContentid)
                .toList(); // response 로 받아온 값중에 contentId만 추출

        // 미리 DB에 존재하는 contentId 가져오기
        List<Long> existingContentIds = festivalRepository.findContentIdsByContentIds(incomingContentIds);
        Set<Long> existingSet = new HashSet<>(existingContentIds); // 중복 제거를 위해 Set 사용
        // List의 시간 연산도는 O(n) 이지만, Set의 시간 복잡도는 O(1)이므로 중복 제거에 유리해서

        List<Festival> festivalsToSave = dtoList.stream()
                .filter(dto -> !existingSet.contains(dto.getContentid()))
                // 필터링하여 이미 존재하는 contentId를 제외
                .map(this::listToEntity)
                .toList();

        if (!festivalsToSave.isEmpty()) {
            festivalRepository.saveAll(festivalsToSave);
            logger.info(" 총 {}개 새로 저장 완료", festivalsToSave.size());
        } else {
            logger.info("저장할 새로운 축제 정보가 없습니다.");
        }
    }

    private String getFoodid(String lang) {
        return switch (lang.toLowerCase()) {
            case "kor" -> "39";
            case "jpn" -> "82";
            case "chn" -> "82";
            case "eng" -> "82";
            case "fra" -> "82";
            case "rus" -> "82";
            case "spa" -> "82";
            default -> "39";
        };
    }
    private String getContentid(String lang) {
        return switch (lang.toLowerCase()) {
            case "kor" -> "15";
            case "jpn" -> "85";
            case "chn" -> "85";
            case "eng" -> "85";
            case "fra" -> "85";
            case "rus" -> "85";
            case "spa" -> "85";
            default -> "15";
        };
    }

    private String getServiceName(String lang) {
        return switch (lang.toLowerCase()) {
            case "kor" -> "KorService2";
            case "jpn" -> "JpnService2";
            case "chn" -> "ChsService2";
            case "eng" -> "EngService2";
            case "fra" -> "FreService2";
            case "rus" -> "RusService2";
            case "spa" -> "SpnService2";
            default -> "KorService2";
        };
    }
}
