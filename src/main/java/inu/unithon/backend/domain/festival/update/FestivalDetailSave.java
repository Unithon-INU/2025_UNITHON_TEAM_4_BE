package inu.unithon.backend.domain.festival.update;

import inu.unithon.backend.domain.festival.dto.FestivalInfoResponseDto;
import inu.unithon.backend.domain.festival.dto.FestivalIntroResponseDto;
import inu.unithon.backend.domain.festival.dto.FestivalResponseDto;
import inu.unithon.backend.domain.festival.repository.FestivalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import inu.unithon.backend.domain.festival.service.FestivalService;
import inu.unithon.backend.domain.festival.entity.FestivalContent;


@Service
@RequiredArgsConstructor
public class FestivalDetailSave {
    private final FestivalService festivalService;
    private final FestivalRepository festivalRepository;

    public void saveDetail(String ContentId){
        FestivalResponseDto festivalResponseDto = festivalService.getFestivalInfo("kor", ContentId);


    }
}
