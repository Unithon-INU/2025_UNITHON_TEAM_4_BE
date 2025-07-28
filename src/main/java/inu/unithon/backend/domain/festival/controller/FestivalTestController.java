package inu.unithon.backend.domain.festival.controller;

import inu.unithon.backend.domain.festival.entity.Festival;
import inu.unithon.backend.domain.festival.entity.FestivalContent;
import inu.unithon.backend.domain.festival.repository.FestivalContentRepository;
import inu.unithon.backend.domain.festival.repository.FestivalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
    contentRepository.save(content);

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
    festivalRepository.save(festival);

  }
}
