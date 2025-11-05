package inu.unithon.backend.domain.festival.service;

import inu.unithon.backend.domain.festival.dto.FestivalInfoResponseDto;
import inu.unithon.backend.domain.festival.dto.FestivalIntroResponseDto;
import inu.unithon.backend.domain.festival.dto.FestivalResponseDto;

public interface FestivalService {
    FestivalResponseDto getFestivalList(String lang, String numOfRows, String pageNo, String eventStartDate, String areaCode, String eventEndDate);
    FestivalResponseDto getFestivalInfo(String lang, String contentId);
    FestivalResponseDto getSearchFestival(String lang, String keyword, String numOfRows, String pageNo);
    FestivalIntroResponseDto getFestivalDetailIntro(String lang, String contentId, String contentTypeId);
    FestivalInfoResponseDto getFestivalDetailInfo(String lang, String contentId, String contentTypeId);
    FestivalResponseDto getFestivalLocationFood(String lang, String MapX, String MapY, String NumOfRows, String PageNo, String Radius);
}
