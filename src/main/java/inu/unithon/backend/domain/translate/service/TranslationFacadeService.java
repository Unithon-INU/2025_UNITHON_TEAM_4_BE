package inu.unithon.backend.domain.translate.service;

import inu.unithon.backend.domain.festival.entity.Festival;
import inu.unithon.backend.domain.festival.repository.FestivalContentRepository;
import inu.unithon.backend.domain.festival.repository.FestivalRepository;
import inu.unithon.backend.domain.translate.entity.TranslateLanguage;
import inu.unithon.backend.domain.translate.repository.sql.festivalTranslate.FestivalTranslateRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TranslationFacadeService implements TranslationService {

  private final TranslationExecutorService executorService;
  private final FestivalRepository festivalRepository;
  private final FestivalContentRepository contentRepository;
  private final FestivalTranslateRepository festivalTranslateRepository;

  @Override
  public void translateAllFestivals() {
    log.info("[Facade] Start batch translation");

    List<Festival> festivals = festivalRepository.findAll();

    for (Festival festival : festivals) {

      // 번역이 이미 존재하면 스킵
      if (festivalTranslateRepository.existsByContentIdAndLanguage(
        festival.getContentId(), TranslateLanguage.kor)) {
        continue;
      }

      executorService.translateFestival(festival.getContentId());
    }

    log.info("[Facade] Batch complete");
  }

  @Override
  public void translateFestival(Long contentId) {
    executorService.translateFestival(contentId);
  }
}
