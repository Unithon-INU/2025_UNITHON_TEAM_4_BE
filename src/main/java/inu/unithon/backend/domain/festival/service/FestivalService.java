package inu.unithon.backend.domain.festival.service;

import inu.unithon.backend.domain.festival.dto.v1.FestivalInfoResponseDto;
import inu.unithon.backend.domain.festival.dto.v1.FestivalIntroResponseDto;
import inu.unithon.backend.domain.festival.dto.v1.FestivalResponseDto;
import inu.unithon.backend.domain.festival.dto.v2.request.FestivalTranslatePeriodSearchRequest;
import inu.unithon.backend.domain.festival.dto.v2.request.FestivalTranslateSearchRequest;
import inu.unithon.backend.domain.festival.dto.v2.response.FestivalTranslateResponse;
import inu.unithon.backend.global.response.PageResponseDto;

public interface FestivalService {
    FestivalResponseDto getFestivalList(String lang, String numOfRows, String pageNo, String eventStartDate, String areaCode, String eventEndDate);
    FestivalResponseDto getFestivalInfo(String lang, String contentId);
    FestivalResponseDto getSearchFestival(String lang, String keyword, String numOfRows, String pageNo);
    FestivalIntroResponseDto getFestivalDetailIntro(String lang, String contentId, String contentTypeId);
    FestivalInfoResponseDto getFestivalDetailInfo(String lang, String contentId, String contentTypeId);
    FestivalResponseDto getFestivalLocationFood(String lang, String MapX, String MapY, String NumOfRows, String PageNo, String Radius);

    /**
     * 키워드로 축제 검색
     * @param festivalTranslateSearchRequest
     * @param page
     * @param size
     * @return
     */
    PageResponseDto<FestivalTranslateResponse> searchFestivalsByKeyword(FestivalTranslateSearchRequest festivalTranslateSearchRequest, int page, int size);

    /**
     * 기간으로 축제 검색
     * @param festivalTranslatePeriodSearchRequest
     * @param page
     * @param size
     * @return
     */
    PageResponseDto<FestivalTranslateResponse> searchFestivalsByPeriod(FestivalTranslatePeriodSearchRequest festivalTranslatePeriodSearchRequest, int page, int size);
}
