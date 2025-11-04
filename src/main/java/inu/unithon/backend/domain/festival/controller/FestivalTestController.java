package inu.unithon.backend.domain.festival.controller;

import inu.unithon.backend.domain.festival.entity.Festival;
import inu.unithon.backend.domain.festival.entity.FestivalContent;
import inu.unithon.backend.domain.festival.entity.FestivalTranslate;
import inu.unithon.backend.domain.festival.repository.festival.FestivalContentRepository;
import inu.unithon.backend.domain.festival.repository.festival.FestivalRepository;
import inu.unithon.backend.domain.festival.service.FestivalCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * Festival, FestivalContent 를 DB에 저장하는 테스트 컨트롤러
 * @author Frozzun
 */
@RestController
@RequestMapping("/api/v1/test")
@RequiredArgsConstructor
public class FestivalTestController {

  private final FestivalRepository festivalRepository;
  private final FestivalContentRepository contentRepository;
  FestivalCommandService festivalCommandService;

  @GetMapping("/putFestival")
  public void putFestival() {

    FestivalContent content = FestivalContent.builder()
      .title("테스트 축제 컨텐트 title")
      .imageUrl("www.testContent.com")
      .address("테스트 축제 컨텐트 address")
      .content("테스트 축제 컨텐트 content")
      .startDate(LocalDateTime.now())
      .endDate(LocalDateTime.now())
      .build();


    Festival festival = Festival.builder()
      .title("테스트 축제 title")
      .imageUrl("www.test.com")
      .address("테스트 축제 address")
      .contentId("1")
      .content("테스트 축제 content")
      .startDate(LocalDateTime.now())
      .endDate(LocalDateTime.now())
      .festivalContent(content)
      .build();

    // set festival in festival content
    content.setFestival(festival);

    FestivalTranslate festivalTranslate = FestivalTranslate.builder()
      .title(festival.getTitle())
      .imageUrl(festival.getImageUrl())
      .address(festival.getAddress())
      .contentId(festival.getContentId())
      .content(festival.getContent())
      .startDate(festival.getStartDate())
      .endDate(festival.getEndDate())
      .festival(festival).build();

    festivalCommandService.createFestivalTranslate(festivalTranslate);
    contentRepository.save(content);
    festivalRepository.save(festival);

  }
}
