package inu.unithon.backend.domain.festival.controller;

import inu.unithon.backend.domain.festival.entity.*;
import inu.unithon.backend.domain.festival.repository.FestivalContentRepository;
import inu.unithon.backend.domain.festival.repository.FestivalRepository;
import inu.unithon.backend.domain.translate.entity.FestivalContentTranslate;
import inu.unithon.backend.domain.translate.entity.FestivalTranslate;
import inu.unithon.backend.domain.translate.entity.TranslateLanguage;
import inu.unithon.backend.domain.translate.repository.sql.festivalContentTranslate.FestivalContentTranslateRepository;
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
  private final FestivalCommandService festivalCommandService;
  private final FestivalContentTranslateRepository translateRepository;

  @GetMapping("/putFestival")
  public void putFestival() {

    FestivalContent content = FestivalContent.builder()
      .title("테스트 축제 컨텐트 title")
      .address("test address")
      .content("테스트 축제 컨텐트 content")
      .overview("OverView")
      .playtime("test playtime")
      .mapx("mapx")
      .mapy("mapy")
      .firstImage("www.testContent.com")
      .firstImage2("image2")
      .areaCode("areaCode")
      .addr1("addr1")
      .tel("0101010111")
      .startDate(LocalDateTime.now())
      .endDate(LocalDateTime.now())
      .build();


    Festival festival = Festival.builder()
      .title("테스트 축제 title")
      .imageUrl("www.test.com")
      .address("테스트 축제 address")
      .contentId(1L)
      .content("테스트 축제 content")
      .startDate(LocalDateTime.now())
      .endDate(LocalDateTime.now())
      .festivalContent(content)
      .build();


    FestivalTranslate festivalTranslate = FestivalTranslate.builder()
      .language(TranslateLanguage.kor)
      .title(festival.getTitle())
      .imageUrl(festival.getImageUrl())
      .address(festival.getAddress())
      .contentId(festival.getContentId())
      .content(festival.getContent())
      .startDate(festival.getStartDate())
      .endDate(festival.getEndDate())
      .festival(festival)
      .build();

    FestivalContentTranslate festivalContentTranslate = FestivalContentTranslate.builder()
      .language(TranslateLanguage.kor)
      .contentId(1L)
      .title("테스트 축제 번역 컨텐츠 제목")
      .address("서울특별시 중구")
      .content("한글 번역 내용입니다.")
      .overview("테스트 축제에 대한 간단한 설명")
      .playtime("09:00 ~ 18:00")
      .firstImage("https://example.com/test.jpg")
      .startDate(LocalDateTime.now())
      .endDate(LocalDateTime.now().plusDays(3))
      .festivalContent(content) // 연관관계 객체 주입
      .build();

    /* 이것도 아래 처럼 바꿔야됨 */

    contentRepository.save(content);
    festivalRepository.save(festival);
    festivalCommandService.createFestivalTranslate(festivalTranslate);
    translateRepository.save(festivalContentTranslate);

  }
}
