package inu.unithon.backend.domain.festival.service;

import inu.unithon.backend.domain.festival.dto.v1.FestivalInfoResponseDto;
import inu.unithon.backend.domain.festival.dto.v1.FestivalIntroResponseDto;
import inu.unithon.backend.domain.festival.dto.v1.FestivalResponseDto;
import inu.unithon.backend.domain.festival.dto.v2.request.FestivalTranslatePeriodSearchRequest;
import inu.unithon.backend.domain.festival.dto.v2.request.FestivalTranslateSearchRequest;
import inu.unithon.backend.domain.festival.dto.v2.response.FestivalTranslateResponse;
import inu.unithon.backend.domain.festival.entity.Festival;
import inu.unithon.backend.domain.festival.entity.FestivalTranslate;
import inu.unithon.backend.domain.festival.entity.TranslateLanguage;
import inu.unithon.backend.domain.festival.mapper.FestivalMapper;
import inu.unithon.backend.domain.festival.repository.festival.FestivalRepository;
import inu.unithon.backend.domain.festival.repository.festivalTranslate.FestivalTranslateRepository;
import inu.unithon.backend.global.response.PageResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import inu.unithon.backend.global.exception.CustomException;
import inu.unithon.backend.global.exception.CommonErrorCode;

import java.net.URI;
import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.ObjectMapper;

@Slf4j
@Service
@RequiredArgsConstructor
public class FestivalServiceImpl implements FestivalService {

    private static final Logger logger = LoggerFactory.getLogger(FestivalServiceImpl.class);
    private final FestivalRepository festivalRepository;
    private final FestivalTranslateRepository translateRepository;
    private final FestivalMapper festivalMapper;

    @Value("${tourapi.service-key}")
    private String encodedServiceKey;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public FestivalResponseDto getFestivalList(String lang, String numOfRows, String pageNo, String eventStartDate, String areaCode, String eventEndDate) {
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
            throw new CustomException(CommonErrorCode.INTERNAL_SERVER_ERROR);
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
            logger.error("Festival info error : ", e);
            throw new CustomException(CommonErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    public FestivalResponseDto getSearchFestival(String lang, String keyword, String numOfRows, String pageNo) {
        try {
            String serviceName = getServiceName(lang);
            String baseUrl = "http://apis.data.go.kr/B551011/";
            String servicePath = serviceName + "/searchKeyword1";
            if ("KorService1".equals(serviceName) || "JpnService1".equals(serviceName) || "ChsService1".equals(serviceName)) {
                keyword = URLEncoder.encode(keyword, StandardCharsets.UTF_8);
            }

            String url = baseUrl + servicePath
                    + "?serviceKey=" + encodedServiceKey
                    + "&MobileApp=UnithonApp"
                    + "&MobileOS=ETC"
                    + "&arrange=A"
                    + "&listYN=Y"
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
            throw new CustomException(CommonErrorCode.INTERNAL_SERVER_ERROR);
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
            logger.error("Festival intro error : ", e);
            throw new CustomException(CommonErrorCode.INTERNAL_SERVER_ERROR);
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
            logger.error("Festival Detail info error : ", e);
            throw new CustomException(CommonErrorCode.INTERNAL_SERVER_ERROR);
        }
    }
    public FestivalResponseDto getFestivalLocationFood(String lang, String MapX, String MapY, String NumOfRows, String PageNo, String radius) {
        try {
            String serviceName = getServiceName(lang);
            String baseUrl = "http://apis.data.go.kr/B551011/";
            String servicePath = serviceName + "/locationBasedList1";

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
            throw new CustomException(CommonErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 축제 키워드 검색
     * @param festivalTranslateSearchRequest : TranslateLanguage language(ENUM), keyword
     * @param page
     * @param size
     * @return
     */
    @Override
    public PageResponseDto<FestivalTranslateResponse> searchFestivalsByKeyword(FestivalTranslateSearchRequest festivalTranslateSearchRequest, int page, int size) {
        String keyWord = festivalTranslateSearchRequest.getKeyword();
        TranslateLanguage lang = festivalTranslateSearchRequest.getLang();
        Pageable pageable = PageRequest.of(page, size);
        log.info("축제 키워드 검색 요청 - keyword: {}, lang: {}, page: {}, size: {}", keyWord, lang, page, size);

        Page<FestivalTranslate> festivalTranslates = translateRepository.findByKeyword(
          lang,
          keyWord,
          pageable
        );
        if (festivalTranslates.isEmpty()) {
            log.info("키워드 검색 결과 없음 - keyword: {}, lang: {}", keyWord, lang);
        }

        log.info("축제 키워드 검색 결과 - totalElements: {}", festivalTranslates.getTotalElements());
        return PageResponseDto.from(festivalTranslates.map(festivalMapper::toResponse));
    }

    /**
     * 기간으로 축제 검색
     *
     * @param festivalTranslatePeriodSearchRequest : TranslateLanguage language(ENUM), StartDate, EndDate
     * @param page
     * @param size
     * @return
     */
    @Override
    public PageResponseDto<FestivalTranslateResponse> searchFestivalsByPeriod(FestivalTranslatePeriodSearchRequest festivalTranslatePeriodSearchRequest, int page, int size) {
        LocalDateTime startDate = festivalTranslatePeriodSearchRequest.getStartDate();
        LocalDateTime endDate = festivalTranslatePeriodSearchRequest.getEndDate();
        TranslateLanguage lang = festivalTranslatePeriodSearchRequest.getLang();
        Pageable pageable = PageRequest.of(page, size);
        log.info("축제 기간 검색 요청 - startDate: {}, endDate: {}, lang: {}, page: {}, size: {}", startDate, endDate, lang, page, size);

        Page<FestivalTranslate> festivalTranslates = translateRepository.findFestivalsByPeriod(
          lang,
          startDate,
          endDate,
          pageable
        );
        if (festivalTranslates.isEmpty()) {
            log.info("기간 검색 결과 없음 - startDate: {}, endDate: {}, lang: {}", startDate, endDate, lang);
        }

        log.info("축제 기간 검색 결과 - totalElements: {}", festivalTranslates.getTotalElements());
        return PageResponseDto.from(festivalTranslates.map(festivalMapper::toResponse));
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
            case "kor" -> "KorService1";
            case "jpn" -> "JpnService1";
            case "chn" -> "ChsService1";
            case "eng" -> "EngService1";
            case "fra" -> "FreService1";
            case "rus" -> "RusService1";
            case "spa" -> "SpnService1";
            default -> "KorService1";
        };
    }

    /**
     * 축제 아이디로 축제를 받아오는 메서드
     * @author : frozzun
     * @param festivalId 축제 Id
     * @return Festival
     */
    public Festival getFestival(Long festivalId) {
        return festivalRepository.findById(festivalId)
          .orElseThrow(() -> new CustomException(CommonErrorCode.FESTIVAL_NOT_FOUND));
    }
}
