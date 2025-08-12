package inu.unithon.backend.domain.festival.service;

import inu.unithon.backend.domain.festival.dto.*;
import inu.unithon.backend.domain.festival.entity.Festival;
import inu.unithon.backend.domain.festival.repository.FestivalRepository;
import inu.unithon.backend.global.rabbitMq.RabbitMqProducer;
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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.time.LocalDateTime;
import java.util.Set;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
@RequiredArgsConstructor
public class FestivalService implements FestivalServiceInterface{

    private static final DateTimeFormatter ymeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
    private final RabbitMqProducer rabbitMqProducer;

    private LocalDateTime parseYmdToDay(String ymd) {
        if (ymd == null || ymd.isBlank()) return null;
        LocalDate date = LocalDate.parse(ymd, ymeFormatter);
        return date.atStartOfDay();
    }

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
        try {
            LocalDateTime startDate = parseYmdToDay(dto.getEventstartdate());
            LocalDateTime endDate = parseYmdToDay(dto.getEventenddate());

            return Festival.builder()
                    .contentId(dto.getContentid())
                    .title(dto.getTitle())
                    .startDate(startDate)
                    .endDate(endDate)
                    .imageUrl(dto.getFirstimage())
                    .build();
        } catch (Exception e){
            throw new CustomException(ErrorCode.DATE_PARSE_ERROR);
        }

    }

    @Transactional
    @Override
    public void saveFestivalList(List<FestivalDto> dtoList) {
        List<Long> newContentIds = dtoList.stream()
                .map(FestivalDto::getContentid)
                // 조회한 데이터 set에서 contentID만을 추출해서 stream을 통해 List 로 변환
                .toList();
        List<Long> oldContentIds = festivalRepository.findContentIdsByContentIds(newContentIds);
        // 변환한 List 를 통해 기존에 있는 data 인지 판별
        Set<Long> oldContentIdSet = new HashSet<>(oldContentIds);
        // 새로운 데이터 중 기존에 없는 데이터만을 모야 set으로

        List<Festival> festivalsToSave = dtoList.stream()
                .filter(dto -> !oldContentIdSet.contains(dto.getContentid()))
                // 앞서 만든 set을 통해 기존에 있는 data는 들어가지 않도록 설정
                .map(this::listToEntity)
                .toList();
        if (!festivalsToSave.isEmpty()) {
            // 앞서 만든 festivalsToSave 리스트는 db에 현재 없는 새로운 데이터들로만 이루어진 Festival 객체들의 리스트 들로 이루어 졌으므로
            // ㅇ; 리스트가 비어있지 않다면 아래 로직을 실행
            festivalRepository.saveAll(festivalsToSave);
            logger.info(" 새로운 축제 정보 저장 완료: {}", festivalsToSave.size());
            festivalsToSave.forEach(f -> rabbitMqProducer.detailSend(String.valueOf(f.getContentId())));
            // 앞서 저장된 객체 리스트들을 각자 하나씩 rabbitMqProducer를 통해 detailSend 메소드를 등록
        } else {
            logger.info(" nothing to new");
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
