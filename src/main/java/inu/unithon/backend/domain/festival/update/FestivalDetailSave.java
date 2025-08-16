package inu.unithon.backend.domain.festival.update;


import inu.unithon.backend.domain.festival.dto.FestivalDto;
import inu.unithon.backend.domain.festival.dto.FestivalResponseDto;
import inu.unithon.backend.domain.festival.entity.Festival;
import inu.unithon.backend.domain.festival.entity.FestivalContent;
import inu.unithon.backend.domain.festival.repository.FestivalContentRepository;
import inu.unithon.backend.domain.festival.service.FestivalServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import inu.unithon.backend.domain.festival.repository.FestivalRepository;
import inu.unithon.backend.domain.festival.service.FestivalService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FestivalDetailSave {

    private final FestivalServiceInterface festivalService;
    private final FestivalContentRepository festivalContentRepository;
    private static final DateTimeFormatter dayFormat = DateTimeFormatter.ofPattern("yyyyMMdd");

    private LocalDateTime TimeSetting(String date) {
        if (date == null || date.isBlank()) {
            return null;
        }
        return LocalDate.parse(date, dayFormat).atStartOfDay();
    }

    private FestivalDto FirstItem(FestivalResponseDto data) {
        if(data == null || data.getResponse() == null) return null;
        var body = data.getResponse().getBody();
        if (body == null || body.getItems() == null) return null;
        List<FestivalDto> list = body.getItems().getItem();
        if (list == null || list.isEmpty()) return null;
        return list.get(0);
    }

    @Transactional
    public void saveDetail(String contentIdStr) {
        long contentId = Long.parseLong(contentIdStr);

        FestivalResponseDto info = festivalService.getFestivalInfo("kor", contentId);
        FestivalDto item = FirstItem(info);
        if(item == null) {
            return;
        }

        String title       = item.getTitle();
        String imageUrl    = item.getFirstimage();
        String address     = item.getAddr1();
        String overview    = item.getOverview();
        String mapx        = item.getMapx();
        String mapy        = item.getMapy();
        String firstImage  = item.getFirstimage();
        String firstImage2 = item.getFirstimage2();
        String areaCode    = item.getAreacode();
        String addr1       = item.getAddr1();
        String tel         = item.getTel();
        LocalDateTime start = TimeSetting(item.getEventstartdate());
        LocalDateTime end   = TimeSetting(item.getEventenddate());


    }
}
