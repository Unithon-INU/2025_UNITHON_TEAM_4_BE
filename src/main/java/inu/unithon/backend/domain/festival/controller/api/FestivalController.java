package inu.unithon.backend.domain.festival.controller.api;

import inu.unithon.backend.domain.festival.controller.docs.FestivalControllerSpecification;
import inu.unithon.backend.domain.festival.dto.FestivalInfoResponseDto;
import inu.unithon.backend.domain.festival.dto.FestivalIntroResponseDto;
import inu.unithon.backend.domain.festival.dto.FestivalResponseDto;
import inu.unithon.backend.domain.festival.service.FestivalServiceImpl;
import inu.unithon.backend.global.response.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import inu.unithon.backend.domain.festival.service.FestivalService;

@RestController
@RequestMapping("/api/v1/festivals")
@RequiredArgsConstructor
public class FestivalController implements FestivalControllerSpecification {

  private final FestivalServiceImpl festivalService;

    /**
     * 축제  조회
     *
     * @param lang            언어 코드 (기본값: "kor")
     * @param numOfRows       한 페이지에 표시할 항목 수 (기본값: "10")
     * @param pageNo          페이지 번호 (기본값: "1")
     * @param eventStartDate  이벤트 시작 날짜 (기본값: "20250601")
     * @param areaCode        지역 코드 (선택 사항)
     * @return 축제 목록 응답
     */
    @Override
    public ResponseEntity<ResponseDto<?>> getFestivalByArea(String lang, String numOfRows, String pageNo, String eventStartDate, String areaCode, String eventEndDate) {
        FestivalResponseDto response = festivalService.getFestivalList(lang, numOfRows, pageNo, eventStartDate, areaCode, eventEndDate);
        return ResponseEntity.ok(ResponseDto.success(response));
    }

    /**
     * 축제 정보 조회
     *
     * @param lang        언어 코드 (기본값: "kor")
     * @param contentId   콘텐츠 Id
     * @return 축제 정보 응답
     */
    @Override
    public ResponseEntity<ResponseDto<?>> getFestivalInfo(String lang, String contentId) {
        FestivalResponseDto response = festivalService.getFestivalInfo(lang, contentId);
        return ResponseEntity.ok(ResponseDto.success(response));
    }

  /**
   * 축제 검색
   *
   * @param lang      언어 코드 (기본값: "kor")
   * @param keyword   검색 키워드
   *  @param numOfRows 한 페이지에 표시할 항목 수 (기본값: "10")
   *  @param pageNo    페이지 번호 (기본값: "1")
   * @return 축제 검색 결과 응답
   * pagination
   */
  @Override
  public ResponseEntity<ResponseDto<?>> searchFestival(String lang, String keyword, String numOfRows, String pageNo) {
    FestivalResponseDto response = festivalService.getSearchFestival(lang, keyword, numOfRows, pageNo);
    return ResponseEntity.ok(ResponseDto.success(response));
  }

  /**
     * 축제 상세 정보 조회
     *
     * @param lang            언어 코드 (기본값: "kor")
     * @param contentId       콘텐츠 Id
     * @param contentTypeId   콘텐츠 타입 Id
     * @return 축제 상세 정보 응답
     */

    @Override
    public ResponseEntity<ResponseDto<?>> getFestivalDetailInfo(String lang, String contentId, String contentTypeId) {
        FestivalInfoResponseDto response = festivalService.getFestivalDetailInfo(lang, contentId, contentTypeId);
        return ResponseEntity.ok(ResponseDto.success(response));
    }
    /**
     * 축제 상세 소개 조회
     *
     * @param lang            언어 코드 (기본값: "kor")
     * @param contentId       콘텐츠 Id
     * @param contentTypeId   콘텐츠 타입 Id
     * @return 축제 상세 소개 응답
     */

    @Override
    public ResponseEntity<ResponseDto<?>> getFestivalDetailIntro(String lang, String contentId, String contentTypeId) {
        FestivalIntroResponseDto response = festivalService.getFestivalDetailIntro(lang, contentId, contentTypeId);
        return ResponseEntity.ok(ResponseDto.success(response));
    }
    /**
     * 축제 위치 음식 조회
     *
     * @param lang      언어 코드 (기본값: "kor")
     * @param MapX     X 좌표
     * @param MapY     Y 좌표
     * @param NumOfRows 한 페이지에 표시할 항목 수 (기본값: "10")
     * @param PageNo   페이지 번호 (기본값: "1")
     * @param Radius   반경 (기본값: "1000")
     * @return 축제 위치 음식 응답
     */
    @Override
    public ResponseEntity<ResponseDto<?>> getFestivalLocationFood(String lang, String MapX, String MapY, String NumOfRows, String PageNo, String Radius) {
        FestivalResponseDto response = festivalService.getFestivalLocationFood(lang, MapX, MapY, NumOfRows, PageNo, Radius);
        return ResponseEntity.ok(ResponseDto.success(response));
    }
}
