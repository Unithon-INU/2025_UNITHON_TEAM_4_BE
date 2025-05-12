package inu.unithon.backend.festival.service;

import inu.unithon.backend.festival.dto.FestivalResponseWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@RequiredArgsConstructor
public class FestivalService {

    private final RestTemplate restTemplate;

    // application.yml 또는 application.properties에서 주입
    @Value("${tourapi.service-key}")
    private String serviceKey;

    public FestivalResponseWrapper getFestivalList(String lang, String areaCode String numOfRows String pageNo) {
        String serviceName = getServiceName(lang); // kor → KorService1 등

        String url = UriComponentsBuilder
                .fromHttpUrl("http://apis.data.go.kr/B551011/" + serviceName + "/areaBasedList1")
                .queryParam("serviceKey", serviceKey)
                .queryParam("MobileApp", "UnithonApp")
                .queryParam("MobileOS", "ETC")
                .queryParam("arrange", "A")
                .queryParam("listYN", "Y")
                .queryParam("_type", "json")
                .queryParam("numOfRows", numOfRows)
                .queryParam("pageNo", pageNo)
                if (areaCode != null && !areaCode.isBlank()) {
                    builder.queryParam("areaCode", areaCode);
                }
                .toUriString();

        return restTemplate.getForObject(url, FestivalResponseWrapper.class);
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
