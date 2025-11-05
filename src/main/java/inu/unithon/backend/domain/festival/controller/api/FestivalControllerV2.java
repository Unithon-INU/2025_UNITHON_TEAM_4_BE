package inu.unithon.backend.domain.festival.controller.api;

import inu.unithon.backend.domain.festival.controller.docs.FestivalControllerSpecification;
import inu.unithon.backend.domain.festival.dto.FestivalInfoResponseDto;
import inu.unithon.backend.domain.festival.dto.FestivalIntroResponseDto;
import inu.unithon.backend.domain.festival.dto.FestivalResponseDto;
import inu.unithon.backend.domain.festival.service.FestivalService;
import inu.unithon.backend.global.response.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v2/festivals")
@RequiredArgsConstructor
public class FestivalControllerV2 implements FestivalControllerSpecification {

  @Qualifier("festivalServiceImplV2")
  private final FestivalService festivalService;

  @Override
  public ResponseEntity<ResponseDto<?>> getFestivalByArea(String lang, String numOfRows, String pageNo, String eventStartDate, String eventEndDate, String areaCode) {
    FestivalResponseDto response = festivalService.getFestivalList(lang, numOfRows, pageNo, eventStartDate, areaCode, eventEndDate);
    return ResponseEntity.ok(ResponseDto.success(response));
  }

  @Override
  public ResponseEntity<ResponseDto<?>> getFestivalInfo(String lang, String contentId) {
    FestivalResponseDto response = festivalService.getFestivalInfo(lang, contentId);
    return ResponseEntity.ok(ResponseDto.success(response));
  }

  @Override
  public ResponseEntity<ResponseDto<?>> searchFestival(String lang, String keyword, String numOfRows, String pageNo) {
    FestivalResponseDto response = festivalService.getSearchFestival(lang, keyword, numOfRows, pageNo);
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

  @Override
  public ResponseEntity<ResponseDto<?>> getFestivalLocationFood(String lang, String MapX, String MapY, String NumOfRows, String PageNo, String radius) {
    FestivalResponseDto response = festivalService.getFestivalLocationFood(lang, MapX, MapY, NumOfRows, PageNo, radius);
    return ResponseEntity.ok(ResponseDto.success(response));
  }
}
