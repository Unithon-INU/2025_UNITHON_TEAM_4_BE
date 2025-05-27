package inu.unithon.backend.domain.festival.service;

import inu.unithon.backend.domain.festival.dto.FestivalResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.net.URI;
import java.util.Optional;



@Service
@RequiredArgsConstructor
public class FestivalService {

    private static final Logger logger = LoggerFactory.getLogger(FestivalService.class);

    @Value("${tourapi.service-key}")
    private String encodedServiceKey;

    private final RestTemplate restTemplate;

    public FestivalResponseDto getFestivalList(String lang, String numOfRows, String pageNo, String eventStartDate, String areaCode) {
        String serviceName = getServiceName(lang);
        String baseUrl = "http://apis.data.go.kr/B551011";
        String servicePath = serviceName + "/searchFestival1";

        // Use UriComponentsBuilder to properly handle the URL encoding
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(baseUrl + servicePath)
                .queryParam("serviceKey", encodedServiceKey)
                .queryParam("MobileApp", "UnithonApp")
                .queryParam("MobileOS", "ETC")
                .queryParam("arrange", "A")
                .queryParam("listYN", "Y")
                .queryParam("_type", "json")
                .queryParam("numOfRows", numOfRows)
                .queryParam("pageNo", pageNo)
                .queryParam("eventStartDate", eventStartDate) // 축제가 5월에 껴있는거 다 가지고 오는 param
                .queryParam("areaCode", areaCode);


        // Get the encoded URL but don't encode the service key again
        String finalUrl = builder.build(false).toUriString();
        System.out.println("📡 인텔리제이 요청 URL: " + finalUrl);
        logger.info("📡 도커 요청 URL: {}", finalUrl.toString());

        return restTemplate.getForObject(finalUrl.toString(), FestivalResponseDto.class);
    }
    public FestivalResponseDto getFestivalInfo(String lang, String contentId) {
        //festival list 에서 가져온 축제들 리스트중에 상세정보를 알고싶을때 활용하는 api
        String serviceName = getServiceName(lang);
        String baseUrl = "http://apis.data.go.kr/B551011";
        String servicePath = serviceName + "/detailCommon1";

        // Use UriComponentsBuilder to properly handle the URL encoding
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(baseUrl + servicePath)
                .queryParam("serviceKey", encodedServiceKey)
                .queryParam("MobileApp", "UnithonApp")
                .queryParam("MobileOS", "ETC")
                .queryParam("contentId", contentId)
                .queryParam("_type", "json")
                .queryParam("defaultYN", "Y")
                .queryParam("firstImageYN", "Y")
                .queryParam("addrinfoYN", "Y")
                .queryParam("mapinfoYN", "Y")
                .queryParam("overviewYN", "Y")
        .build(false);



//        // Get the encoded URL but don't encode the service key again
////        String finalUrl = String.valueOf(builder.build(false));
//        System.out.println("📡 인텔리제이 요청 URL: " + finalUrl);
//        logger.info("📡 도커 요청 URL: {}", finalUrl);

        return restTemplate.getForObject(builder.toUri(), FestivalResponseDto.class);
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
    } //어떤 언어로 요청했는지에 따라 서비스 이름을 다르게 설정하는 메서드
}
