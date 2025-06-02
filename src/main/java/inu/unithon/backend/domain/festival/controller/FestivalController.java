package inu.unithon.backend.domain.festival.controller;

import inu.unithon.backend.domain.festival.dto.FestivalResponseDto;
import inu.unithon.backend.domain.festival.service.FestivalService;
import inu.unithon.backend.global.response.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/festivals")
@RequiredArgsConstructor
public class FestivalController {

    private final FestivalService festivalService;

    @GetMapping("/list")
    public ResponseEntity<ResponseDto<?>> getFestivalByArea(
            @RequestParam(defaultValue = "kor") String lang,
            @RequestParam(defaultValue = "10") String numOfRows,
            @RequestParam(defaultValue = "1") String pageNo,
            @RequestParam(defaultValue = "20250601") String eventStartDate,
            @RequestParam(required = false) String areaCode
    ) {
        FestivalResponseDto response = festivalService.getFestivalList(lang, numOfRows, pageNo, eventStartDate, areaCode);
        return ResponseEntity.ok(ResponseDto.success(response));
    }
    @GetMapping("/info")
    public ResponseEntity<ResponseDto<?>> getFestivalInfo(
            @RequestParam(defaultValue = "kor") String lang,
            @RequestParam String contentId
    ) {
        FestivalResponseDto response = festivalService.getFestivalInfo(lang, contentId);
        return ResponseEntity.ok(ResponseDto.success(response));
    }
    @GetMapping("/search")
    public ResponseEntity<ResponseDto<?>> searchFestival(
            @RequestParam(defaultValue = "kor") String lang,
            @RequestParam String keyword
    ) {
        FestivalResponseDto response = festivalService.getSearchFestival(lang, keyword);
        return ResponseEntity.ok(ResponseDto.success(response));
    }

    @GetMapping("/detailInfo")
    public ResponseEntity<ResponseDto<?>> getFestivalDetailInfo(
            @RequestParam(defaultValue = "kor") String lang,
            @RequestParam String contentId,
            @RequestParam String contentTypeId
    ) {
        FestivalResponseDto response = festivalService.getFestivalDetailInfo(lang, contentId, contentTypeId);
        return ResponseEntity.ok(ResponseDto.success(response));
    }

    @GetMapping("/detailIntro")
    public ResponseEntity<ResponseDto<?>> getFestivalDetailIntro(
            @RequestParam(defaultValue = "kor") String lang,
            @RequestParam String contentId,
            @RequestParam String contentTypeId
    ) {
        FestivalResponseDto response = festivalService.getFestivalDetailIntro(lang, contentId, contentTypeId);
        return ResponseEntity.ok(ResponseDto.success(response));
    }
}

