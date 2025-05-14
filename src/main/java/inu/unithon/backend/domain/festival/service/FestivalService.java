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

    @Value("${tourapi.service-key}")
    private String encodedServiceKey; // 인코딩된 값 그대로

    private final RestTemplate restTemplate;

    public FestivalResponseDto getFestivalList(String lang, String areaCode, String numOfRows, String pageNo) {

        String serviceName = getServiceName(lang);

        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/B551011/");
        urlBuilder.append(serviceName).append("/areaBasedList1")
                .append("?serviceKey=").append(encodedServiceKey)
                .append("&MobileApp=UnithonApp")
                .append("&MobileOS=ETC")
                .append("&arrange=A")
                .append("&listYN=Y")
                .append("&_type=json")
                .append("&numOfRows=").append(numOfRows)
                .append("&pageNo=").append(pageNo);
        // queryparam 방식을 넣을라고 했는데 docodedServiceKey가 encodedServiceKey로 바뀌는데 그 과정에서 url
        // 우리가 받은 encoded 된 키값과 다르게 인코딩 되어서 강제로 .append 로 수동조립 방식으로 했는데 이거 바꿔야하나여???????

        if (areaCode != null && !areaCode.isBlank()) {
            urlBuilder.append("&areaCode=").append(areaCode);
        }

        String finalUrl = urlBuilder.toString();

        // 테스트용ㅇㅇㅇ
        System.out.println("📡 실제 요청 URL: " + finalUrl);

        return restTemplate.getForObject(finalUrl, FestivalResponseDto.class);
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
