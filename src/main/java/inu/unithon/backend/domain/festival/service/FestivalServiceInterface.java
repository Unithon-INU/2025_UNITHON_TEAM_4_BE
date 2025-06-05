package inu.unithon.backend.domain.festival.service;

import inu.unithon.backend.domain.festival.dto.*;

public interface FestivalServiceInterface {
    FestivalResponseDto getFestivalList(String lang, String numOfRows, String pageNo, String eventStartDate, String areaCode);
    FestivalResponseDto getFestivalInfo(String lang, String contentId);
    FestivalResponseDto getSearchFestival(String lang, String keyword);
    FestivalIntroResponseDto getFestivalDetailIntro(String lang, String contentId, String contentTypeId);
    FestivalInfoResponseDto getFestivalDetailInfo(String lang, String contentId, String contentTypeId);
}
