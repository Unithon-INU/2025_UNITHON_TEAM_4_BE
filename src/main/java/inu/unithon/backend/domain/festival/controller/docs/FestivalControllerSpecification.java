package inu.unithon.backend.domain.festival.controller.docs;

import inu.unithon.backend.domain.festival.dto.v2.request.FestivalTranslatePeriodSearchRequest;
import inu.unithon.backend.domain.festival.dto.v2.request.FestivalTranslateSearchRequest;
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

    /**
     * version - 2
     * 축제 키워드 검색
     *
     * @param request
     * @param page
     * @param size
     * @return
     */
    @PostMapping("/search/keyword")
    ResponseEntity<ResponseDto<?>> searchFestivalByKeyword(
            @Valid @RequestBody FestivalTranslateSearchRequest request,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    );

    /**
     * version - 2
     * 축제 기간별 검색
     *
     * @param request
     * @param page
     * @param size
     * @return
     */
    @PostMapping("/search/period")
    ResponseEntity<ResponseDto<?>> searchFestivalByPeriod(
            @Valid @RequestBody FestivalTranslatePeriodSearchRequest request,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
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
