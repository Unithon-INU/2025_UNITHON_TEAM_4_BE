package inu.unithon.backend.domain.festival.update;

import inu.unithon.backend.domain.festival.dto.FestivalInfoResponseDto;
import inu.unithon.backend.domain.festival.dto.FestivalIntroResponseDto;
import inu.unithon.backend.domain.festival.dto.FestivalResponseDto;
import inu.unithon.backend.domain.festival.repository.FestivalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import inu.unithon.backend.domain.festival.service.FestivalService;


@Service
@RequiredArgsConstructor
public class FestivalDetailSave {
    private final FestivalService festivalService;
    private final FestivalRepository festivalRepository;

    public void saveDetail(String ContentId){
        FestivalResponseDto festivalResponseDto = festivalService.getFestivalInfo("kor", ContentId);
        FestivalIntroResponseDto festivalIntroResponseDto = festivalService.getFestivalDetailIntro("kor", ContentId, "15");
        FestivalInfoResponseDto festivalInfoResponseDto = festivalService.getFestivalDetailInfo("kor", ContentId, "15");
    }
}
