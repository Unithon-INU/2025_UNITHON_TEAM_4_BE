package inu.unithon.backend.domain.translate.service;

import inu.unithon.backend.domain.festival.entity.Festival;
import inu.unithon.backend.domain.festival.entity.FestivalContent;
import inu.unithon.backend.domain.festival.repository.FestivalContentRepository;
import inu.unithon.backend.domain.festival.repository.FestivalRepository;
import inu.unithon.backend.domain.festival.service.FestivalCommandService;
import inu.unithon.backend.domain.translate.client.PapagoClient;
import inu.unithon.backend.domain.translate.entity.FestivalContentTranslate;
import inu.unithon.backend.domain.translate.entity.FestivalTranslate;
import inu.unithon.backend.domain.translate.entity.TranslateLanguage;
import inu.unithon.backend.domain.translate.repository.sql.festivalContentTranslate.FestivalContentTranslateRepository;
import inu.unithon.backend.domain.translate.repository.sql.festivalTranslate.FestivalTranslateRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class TranslationExecutorService {

  private final FestivalRepository festivalRepository;
  private final FestivalContentRepository contentRepository;
  private final FestivalTranslateRepository festivalTranslateRepository;
  private final FestivalContentTranslateRepository festivalContentTranslateRepository;
  private final FestivalCommandService festivalCommandService;
  private final PapagoClient papagoClient;

  public void translateFestival(Long contentId) {

    Festival festival = festivalRepository.findByContentId(contentId)
      .orElse(null);
    FestivalContent content = contentRepository.findByContentId(contentId)
      .orElse(null);

    if (festival == null || content == null) {
      log.warn("원본 축제 정보 부족 → 번역 스킵: contentId={}, festivalExists={}, contentExists={}",
        contentId, festival != null, content != null);
      return; // 또는 continue;
    }

    for (TranslateLanguage lang : TranslateLanguage.values()) {

      if (lang == TranslateLanguage.kor) {
        saveOriginal(festival, content, contentId, lang);
      } else {
        saveTranslated(festival, content, contentId, lang);
      }
    }
  }

  private void deleteExisting(Long contentId, TranslateLanguage lang) {
    festivalTranslateRepository.deleteByContentIdAndLanguage(contentId, lang);
    festivalContentTranslateRepository.deleteByContentIdAndLanguage(contentId, lang);
  }

  private void saveOriginal(Festival festival, FestivalContent content,
                            Long contentId, TranslateLanguage lang) {

    FestivalTranslate ft = FestivalTranslate.builder()
      .language(lang)
      .title(festival.getTitle())
      .address(festival.getAddress())
      .imageUrl(festival.getImageUrl())
      .contentId(contentId)
      .festival(festival)
      .startDate(festival.getStartDate())
      .endDate(festival.getEndDate())
      .build();

    festivalTranslateRepository.save(ft);
    festivalCommandService.createFestivalTranslate(ft);

    FestivalContentTranslate fct = FestivalContentTranslate.builder()
      .language(lang)
      .contentId(contentId)
      .title(content.getTitle())
      .overview(content.getOverview())
      .address(content.getAddress())
      .playtime(content.getPlaytime())
      .mapx(content.getMapx())
      .mapy(content.getMapy())
      .firstImage(content.getFirstImage())
      .firstImage2(content.getFirstImage2())
      .areaCode(content.getAreaCode())
      .addr1(content.getAddr1())
      .tel(content.getTel())
      .dist(content.getDist())
      .startDate(content.getStartDate())
      .endDate(content.getEndDate())
      .festivalContent(content)
      .build();

    festivalContentTranslateRepository.save(fct);
  }

  private void saveTranslated(Festival festival, FestivalContent content,
                              Long contentId, TranslateLanguage lang) {

    FestivalTranslate ft = FestivalTranslate.builder()
      .language(lang)
      .title(papagoClient.translate(festival.getTitle(), "ko", lang.getCode()))
      .address(papagoClient.translate(festival.getAddress(), "ko", lang.getCode()))
      .imageUrl(festival.getImageUrl())
      .contentId(contentId)
      .festival(festival)
      .startDate(festival.getStartDate())
      .endDate(festival.getEndDate())
      .build();

    festivalTranslateRepository.save(ft);
    festivalCommandService.createFestivalTranslate(ft);

    FestivalContentTranslate fct = FestivalContentTranslate.builder()
      .language(lang)
      .contentId(contentId)
      .title(papagoClient.translate(content.getTitle(), "ko", lang.getCode()))
      .overview(papagoClient.translate(content.getOverview(), "ko", lang.getCode()))
      .address(papagoClient.translate(content.getAddress(), "ko", lang.getCode()))
      .playtime(content.getPlaytime())
      .mapx(content.getMapx())
      .mapy(content.getMapy())
      .firstImage(content.getFirstImage())
      .firstImage2(content.getFirstImage2())
      .areaCode(content.getAreaCode())
      .addr1(papagoClient.translate(content.getAddr1(), "ko", lang.getCode()))
      .tel(content.getTel())
      .dist(content.getDist())
      .startDate(content.getStartDate())
      .endDate(content.getEndDate())
      .festivalContent(content)
      .build();

    festivalContentTranslateRepository.save(fct);
  }
}

