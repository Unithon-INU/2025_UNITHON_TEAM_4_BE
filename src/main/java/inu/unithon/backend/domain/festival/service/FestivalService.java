package inu.unithon.backend.festival.service;

import inu.unithon.backend.festival.dto.FestivalResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Service
@RequiredArgsConstructor
public class FestivalService {

    private final RestTemplate restTemplate;
    private static final Logger log = LoggerFactory.getLogger(FestivalService.class);


    // application.yml 또는 application.properties에서 주입
    @Value("${tourapi.service-key}")
    private String serviceKey;

    public FestivalResponseDto getFestivalList(String lang, String areaCode, String numOfRows, String pageNo) {
        //tour api 를 활용하여 축제 리스트 불러오기 (변수값으로 지역코드, 페이지번호, 언어코드, 한페이지에 보여줄 갯수)
        //일단 기본값으로 언어는 korean 지역은 서울로 정함 ㅇㅇ
        String serviceName = getServiceName(lang);
        // 상태관리로 넘겨온 언어코드를 조건문으로 걸어 어떤 언어로 api를 호출할것인지 체크하는 변수설정

        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl("http://apis.data.go.kr/B551011/" + serviceName + "/areaBasedList1")
                .queryParam("serviceKey", serviceKey) // appconfig에서 설정하여 주입하여 넣는다 아마도 보안관련해서 더 좋을거 같다고 판단하여 넣었삼
                .queryParam("MobileApp", "UnithonApp")
                .queryParam("MobileOS", "ETC")
                .queryParam("arrange", "A")
                .queryParam("listYN", "Y")
                .queryParam("_type", "json") // 모든 tour api 호출시 어떤 타입으로 받을지 설정해야 하는데 json으로 설정 가능
                .queryParam("numOfRows", numOfRows)
                .queryParam("pageNo", pageNo);

        if (areaCode != null && !areaCode.isBlank()) {
            builder.queryParam("areaCode", areaCode);
        }

        String url = builder.toUriString();
        log.info("📡 테스트 호출 URL: {}", url);

        return restTemplate.getForObject(url, FestivalResponseDto.class);
    }


    private String getServiceName(String lang) {
        return switch (lang.toLowerCase()) {
            //언어코드 조건문(추후 변경 가능성 있음)
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
