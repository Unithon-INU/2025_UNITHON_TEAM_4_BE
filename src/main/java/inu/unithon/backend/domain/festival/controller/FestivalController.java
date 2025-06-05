package inu.unithon.backend.domain.festival.controller;

import inu.unithon.backend.domain.festival.controller.api.FestivalApi;
import inu.unithon.backend.domain.festival.dto.*;
import inu.unithon.backend.domain.festival.service.FestivalService;
import inu.unithon.backend.global.response.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import inu.unithon.backend.domain.festival.service.FestivalServiceInterface;

@RestController
@RequestMapping("/api/festivals")
@RequiredArgsConstructor
public class FestivalController implements FestivalApi {

    private final  FestivalServiceInterface festivalService;

    @Override
    public ResponseEntity<ResponseDto<?>> getFestivalByArea(String lang, String numOfRows, String pageNo, String eventStartDate, String areaCode) {
        FestivalResponseDto response = festivalService.getFestivalList(lang, numOfRows, pageNo, eventStartDate, areaCode);
        return ResponseEntity.ok(ResponseDto.success(response));
    }

    @Override
    public ResponseEntity<ResponseDto<?>> getFestivalInfo(String lang, String contentId) {
        FestivalResponseDto response = festivalService.getFestivalInfo(lang, contentId);
        return ResponseEntity.ok(ResponseDto.success(response));
    }

    @Override
    public ResponseEntity<ResponseDto<?>> searchFestival(String lang, String keyword) {
        FestivalResponseDto response = festivalService.getSearchFestival(lang, keyword);
        return ResponseEntity.ok(ResponseDto.success(response));
    }

    @Override
    public ResponseEntity<ResponseDto<?>> getFestivalDetailInfo(String lang, String contentId, String contentTypeId) {
        FestivalInfoResponseDto response = festivalService.getFestivalDetailInfo(lang, contentId, contentTypeId);
        return ResponseEntity.ok(ResponseDto.success(response));
    }

    @Override
    public ResponseEntity<ResponseDto<?>> getFestivalDetailIntro(String lang, String contentId, String contentTypeId) {
        FestivalIntroResponseDto response = festivalService.getFestivalDetailIntro(lang, contentId, contentTypeId);
        return ResponseEntity.ok(ResponseDto.success(response));
    }
}
