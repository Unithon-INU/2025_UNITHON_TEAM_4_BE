package inu.unithon.backend.domain.festival.update;

import inu.unithon.backend.domain.festival.dto.v1.FestivalDto;
import inu.unithon.backend.domain.festival.dto.v1.FestivalResponseDto;
import inu.unithon.backend.domain.festival.entity.Festival;
import inu.unithon.backend.domain.festival.entity.FestivalContent;
import inu.unithon.backend.domain.festival.repository.FestivalContentRepository;
import inu.unithon.backend.domain.festival.service.FestivalSaveService;
import inu.unithon.backend.domain.festival.service.FestivalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import inu.unithon.backend.domain.festival.repository.FestivalRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FestivalDetailSave {

    private final FestivalService festivalService;
    private final FestivalSaveService festivalSaveService;
    private final FestivalContentRepository festivalContentRepository;
    private final FestivalRepository festivalRepository;
    private static final DateTimeFormatter dayFormat = DateTimeFormatter.ofPattern("yyyyMMdd");


//    private LocalDateTime TimeSetting(String date) {
//        if (date == null || date.isBlank()) {
//            return null;
//        }
//        return LocalDate.parse(date, dayFormat).atStartOfDay();
//    }

    private FestivalDto FirstItem(FestivalResponseDto data) {
        if(data == null || data.getResponse() == null) return null;
        var body = data.getResponse().getBody();
        if (body == null || body.getItems() == null) return null;
        List<FestivalDto> list = body.getItems().getItem();
        if (list == null || list.isEmpty()) return null;
        return list.get(0);

        // 리스트의 첫번째 아이템만 이용하는 이유는 api response가 리스트로 주지만 그중 첫번째 요소에만 모든 정보가 담겨서 오기때문에
        // 첫번째 아이템을 꺼내와서 사용하는 방법으로 생각
        // 그냥 리스폰스 자체를 사용하게 된다면 객체가 아닌 객체리스트로 받기떄문에 item.item.item 이런식으로 갈수있기때문에 오류 방지하고자
    }

    @Transactional
    public void saveDetail(String contentIdStr) {

        long contentId = Long.parseLong(contentIdStr);

        Festival parent = festivalRepository.findByContentId(contentId)
                .orElseThrow();
        // detail common APi 에서 축제 시작일과 종료일을 주는줄 알았는데 주지않아서 기존 리스트에서 호출해서 받아온 값을 사용하기 위해
        // 그냥 festival repo 에서 현재 ContentId 일치하는 Data에서 시작일과 종료일 추출



        FestivalResponseDto info = festivalService.getFestivalInfo("kor", contentIdStr);
        FestivalDto item = FirstItem(info);
        if(item == null) {
            return;
        }

        String title       = item.getTitle();
        String address     = item.getAddr1();
        String overview    = item.getOverview();
        String mapx        = item.getMapx();
        String mapy        = item.getMapy();
        String firstImage  = item.getFirstimage();
        String firstImage2 = item.getFirstimage2();
        String areaCode    = item.getAreacode();
        String addr1       = item.getAddr1();
        String tel         = item.getTel();
        LocalDateTime start = parent.getStartDate();
        LocalDateTime end   = parent.getEndDate();


        FestivalContent entity = festivalContentRepository.findByContentId(contentId)
                .orElseGet(() -> FestivalContent.builder().contentId(contentId).build());

        entity.updateFromInfo(
                title, address, start, end, overview,
                mapx, mapy,
                firstImage, firstImage2, areaCode, addr1, tel
        );
        festivalContentRepository.save(entity);
    }
}
