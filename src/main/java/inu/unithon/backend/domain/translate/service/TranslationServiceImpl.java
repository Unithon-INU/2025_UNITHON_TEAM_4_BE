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
import inu.unithon.backend.domain.translate.repository.es.festivalContentTranslate.FestivalContentTranslateDocumentRepository;
import inu.unithon.backend.domain.translate.repository.es.festivalTranslate.FestivalTranslateDocumentRepository;
import inu.unithon.backend.domain.translate.repository.sql.festivalContentTranslate.FestivalContentTranslateRepository;
import inu.unithon.backend.domain.translate.repository.sql.festivalTranslate.FestivalTranslateRepository;
import inu.unithon.backend.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static inu.unithon.backend.domain.translate.entity.TranslateLanguage.kor;
import static inu.unithon.backend.global.exception.FestivalErrorCode.FESTIVAL_CONTENT_NOT_FOUND;
import static inu.unithon.backend.global.exception.FestivalErrorCode.FESTIVAL_NOT_FOUND;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TranslationServiceImpl implements TranslationService{

  private final FestivalRepository festivalRepository;
  private final FestivalContentRepository contentRepository;
  private final FestivalTranslateRepository festivalTranslateRepository;
  private final FestivalContentTranslateRepository festivalContentTranslateRepository;

  private final FestivalCommandService festivalCommandService;

  private final PapagoClient papagoClient;

  @Transactional
  @Override
  public void translateAllFestivals() {
    log.info("[TranslationService] Start batch translation for all festivals");

    // 1. DB에서 모든 Festival + FestivalContent 조회
    List<Festival> festivals = festivalRepository.findAll();

    for (Festival festival : festivals) {
      FestivalContent content = contentRepository.findByContentId(festival.getContentId())
        .orElseThrow(() -> new CustomException(FESTIVAL_CONTENT_NOT_FOUND));

      // TODO 임시 : contentId DB에 있으면 번역 XX
      if(festivalTranslateRepository.existsByContentIdAndLanguage(festival.getContentId(), kor)) continue;

      // 기존 메서드 재사용: 단일 contentId 번역
      TranslateFestival(festival.getContentId());
    }

    log.info("[TranslationService] Completed batch translation for all festivals. Total festivals: {}", festivals.size());
  }

  // todo : ElasticSearch repo 관련 로직도 작성해야됨
  @Transactional
  @Override
  public void TranslateFestival(Long contentId) {
    log.info("[TranslationService] Start festival translation for contentId={}", contentId);

    Festival festival = festivalRepository.findByContentId(contentId)
      .orElseThrow(() -> new CustomException(FESTIVAL_NOT_FOUND));

    FestivalContent content = contentRepository.findByContentId(contentId)
      .orElseThrow(() -> new CustomException(FESTIVAL_CONTENT_NOT_FOUND));

    for (TranslateLanguage lang : TranslateLanguage.values()) {
      log.info("[TranslationService] Processing language={} for contentId={}", lang.name(), contentId);
      deleteExistingTranslations(contentId, lang);

      if (lang == kor) {
        log.debug("[TranslationService] Skipping translation (Korean original).");
        saveOriginalTranslations(festival, content, contentId, lang);
      } else {
        log.debug("[TranslationService] Translating from Korean to {}...", lang.getCode());
        saveTranslatedFestival(festival, content, contentId, lang);
      }
    }
    log.info("[TranslationService] Completed all translations for contentId={}", contentId);
  }

  private void deleteExistingTranslations(Long contentId, TranslateLanguage lang) {
    boolean deleted = false;
    if (festivalTranslateRepository.existsByContentIdAndLanguage(contentId, lang)) {
      festivalTranslateRepository.deleteByContentIdAndLanguage(contentId, lang);
      deleted = true;
    }
    if (festivalContentTranslateRepository.existsByContentIdAndLanguage(contentId, lang)) {
      festivalContentTranslateRepository.deleteByContentIdAndLanguage(contentId, lang);
      deleted = true;
    }
    if (deleted) {
      log.debug("[TranslationService] Existing translations deleted for contentId={}, lang={}", contentId, lang);
    }
  }

  /**
   * 한국어(원문) 데이터 저장
   * @param festival
   * @param content
   * @param contentId
   * @param lang
   */
  private void saveOriginalTranslations(Festival festival, FestivalContent content, Long contentId, TranslateLanguage lang) {
    FestivalTranslate festivalTranslate = FestivalTranslate.builder()
      .language(lang) // kor
      .title(festival.getTitle())
      .imageUrl(festival.getImageUrl())
      .address(festival.getAddress())
      .contentId(contentId)
      .startDate(festival.getStartDate())
      .endDate(festival.getEndDate())
      .festival(festival)
      .build();

    festivalTranslateRepository.save(festivalTranslate);
    log.debug("[TranslationService] Saved original FestivalTranslate for contentId={}, lang={}", contentId, lang);

    /* Save to Es */
    festivalCommandService.createFestivalTranslate(festivalTranslate);
    log.debug("[TranslationService] Saved Elastic FestivalTranslate for contentId={}, lang={}", contentId, lang);

    if (content != null) {
      FestivalContentTranslate contentTranslate = buildFestivalContentTranslate(content, contentId, lang, false);
      festivalContentTranslateRepository.save(contentTranslate);
      log.debug("[TranslationService] Saved original FestivalContentTranslate for contentId={}, lang={}", contentId, lang);
    }
  }

  /**
   * 다른 언어로 Papago 번역 후 저장
   * @param festival
   * @param content
   * @param contentId
   * @param lang
   */
  private void saveTranslatedFestival(Festival festival, FestivalContent content, Long contentId, TranslateLanguage lang) {
    try {
      FestivalTranslate translatedFestival = FestivalTranslate.builder()
        .language(lang)
        .title(papagoClient.translate(festival.getTitle(), "ko", lang.getCode()))
        .imageUrl(festival.getImageUrl())
        .address(papagoClient.translate(festival.getAddress(), "ko", lang.getCode()))
        .contentId(contentId)
        .startDate(festival.getStartDate())
        .endDate(festival.getEndDate())
        .festival(festival)
        .build();

      festivalTranslateRepository.save(translatedFestival);
      log.debug("[TranslationService] Saved translated FestivalTranslate for contentId={}, lang={}", contentId, lang);

      /* Save to Es */
      festivalCommandService.createFestivalTranslate(translatedFestival);
      log.debug("[TranslationService] Saved Elastic FestivalTranslate for contentId={}, lang={}", contentId, lang);

      if (content != null) {
        FestivalContentTranslate translatedContent = buildFestivalContentTranslate(content, contentId, lang, true);
        festivalContentTranslateRepository.save(translatedContent);
        log.debug("[TranslationService] Saved translated FestivalContentTranslate for contentId={}, lang={}", contentId, lang);
      }

    } catch (Exception e) {
      log.error("[TranslationService] Translation failed for contentId={}, lang={}, error={}", contentId, lang, e.getMessage(), e);
      throw e;
    }
  }

  /**
   * DB에 저장할 FestivalContentTranslate 엔티티 생성
   * @param c
   * @param contentId
   * @param lang
   * @param translate
   * @return
   */
  private FestivalContentTranslate buildFestivalContentTranslate(FestivalContent c, Long contentId, TranslateLanguage lang, boolean translate) {
    return FestivalContentTranslate.builder()
      .language(lang)
      .contentId(contentId)
      .title(translate ? papagoClient.translate(c.getTitle(), "ko", lang.getCode()) : c.getTitle())
      .address(translate ? papagoClient.translate(c.getAddress(), "ko", lang.getCode()) : c.getAddress())
      .overview(translate ? papagoClient.translate(c.getOverview(), "ko", lang.getCode()) : c.getOverview())
      .playtime(c.getPlaytime())
      .mapx(c.getMapx())
      .mapy(c.getMapy())
      .firstImage(c.getFirstImage())
      .firstImage2(c.getFirstImage2())
      .areaCode(c.getAreaCode())
      .addr1(translate ? papagoClient.translate(c.getAddr1(), "ko", lang.getCode()) : c.getAddr1())
      .tel(c.getTel())
      .dist(c.getDist())
      .startDate(c.getStartDate())
      .endDate(c.getEndDate())
      .festivalContent(c)
      .build();
  }
}
