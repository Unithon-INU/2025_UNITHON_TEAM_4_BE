package inu.unithon.backend.domain.festival.controller.api;

import inu.unithon.backend.domain.festival.dto.*;
import inu.unithon.backend.global.response.ResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public interface FestivalApi {

    @GetMapping("/list")
    ResponseEntity<ResponseDto<?>> getFestivalByArea(
            @RequestParam(defaultValue = "kor") String lang,
            @RequestParam(defaultValue = "10") String numOfRows,
            @RequestParam(defaultValue = "1") String pageNo,
            @RequestParam(defaultValue = "20250601") String eventStartDate,
            @RequestParam(required = false) String areaCode
    );

    @GetMapping("/info")
    ResponseEntity<ResponseDto<?>> getFestivalInfo(
            @RequestParam(defaultValue = "kor") String lang,
            @RequestParam String contentId
    );

    @GetMapping("/search")
    ResponseEntity<ResponseDto<?>> searchFestival(
            @RequestParam(defaultValue = "kor") String lang,
            @RequestParam(defaultValue = "10") String numOfRows,
            @RequestParam(defaultValue = "1") String pageNo,
            @RequestParam String keyword
    );

    @GetMapping("/detailInfo")
    ResponseEntity<ResponseDto<?>> getFestivalDetailInfo(
            @RequestParam(defaultValue = "kor") String lang,
            @RequestParam String contentId,
            @RequestParam String contentTypeId
    );

    @GetMapping("/detailIntro")
    ResponseEntity<ResponseDto<?>> getFestivalDetailIntro(
            @RequestParam(defaultValue = "kor") String lang,
            @RequestParam String contentId,
            @RequestParam String contentTypeId
    );
    @GetMapping("/locationFood")
    ResponseEntity<ResponseDto<?>> getFestivalLocationFood(
            @RequestParam(defaultValue = "kor") String lang,
            @RequestParam String MapX,
            @RequestParam String MapY,
            @RequestParam String NumOfRows,
            @RequestParam String PageNo,
            @RequestParam String radius
    );
}
