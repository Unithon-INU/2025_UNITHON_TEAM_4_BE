package inu.unithon.backend.domain.festival.controller.docs;

import inu.unithon.backend.domain.festival.dto.FestivalResponseDto;
import inu.unithon.backend.global.response.ResponseDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public interface FestivalControllerSpecification {

    @GetMapping("/list")
    ResponseEntity<ResponseDto<?>> getFestivalByArea(
            @RequestParam(defaultValue = "kor") String lang,
            @RequestParam(defaultValue = "10") String numOfRows,
            @RequestParam(defaultValue = "1") String pageNo,
            @RequestParam(defaultValue = "20250601") String eventStartDate,
            @RequestParam(required = false) String eventEndDate,
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

    // todo : keyword 검색
}
