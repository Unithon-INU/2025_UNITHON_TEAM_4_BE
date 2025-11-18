package inu.unithon.backend.domain.festival.service;

import inu.unithon.backend.domain.translate.document.FestivalContentTranslateDocument;
import inu.unithon.backend.domain.translate.document.FestivalTranslateDocument;
import inu.unithon.backend.domain.translate.entity.FestivalContentTranslate;
import inu.unithon.backend.domain.translate.entity.FestivalTranslate;
import inu.unithon.backend.domain.festival.mapper.FestivalMapper;
import inu.unithon.backend.domain.translate.entity.TranslateLanguage;
import inu.unithon.backend.domain.translate.repository.es.festivalContentTranslate.FestivalContentTranslateDocumentRepository;
import inu.unithon.backend.domain.translate.repository.es.festivalTranslate.FestivalTranslateDocumentRepository;
import inu.unithon.backend.domain.translate.repository.sql.festivalContentTranslate.FestivalContentTranslateRepository;
import inu.unithon.backend.domain.translate.repository.sql.festivalTranslate.FestivalTranslateRepository;
import inu.unithon.backend.global.exception.CustomException;
import inu.unithon.backend.global.exception.FestivalErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class FestivalCommandServiceImpl implements FestivalCommandService {

  private final FestivalTranslateDocumentRepository translateDocumentRepository;
  private final FestivalContentTranslateDocumentRepository contentTranslateDocumentRepository;
  private final FestivalMapper mapper;

  /**
   * DB insert 시 ES 자동 인덱싱
   */
  @Override
  public void createFestivalTranslate(FestivalTranslate entity) {
    FestivalTranslateDocument doc = mapper.toDocumentFromFestivalTranslate(entity);
    translateDocumentRepository.save(doc);

    log.debug("Elasticsearch 인덱싱 완료(FestivalTranslate): [title={}, lang={}]", entity.getTitle(), entity.getLanguage());
  }

  /**
   * ES 삭제
   */
  @Override
  public void deleteFestivalTranslate(Long contentId, TranslateLanguage language) {
    String esId = contentId + "_" + language;

    // Elasticsearch 삭제
    translateDocumentRepository.deleteById(esId);
    log.info("Elasticsearch Document 삭제 완료(FestivalTranslate) : esId={}", esId);
  }

  @Override
  public void createFestivalContentTranslate(FestivalContentTranslate entity) {
    FestivalContentTranslateDocument doc = mapper.toDocumentFromFestivalContentTranslate(entity);
    contentTranslateDocumentRepository.save(doc);

    log.info("Elasticsearch 인덱싱 완료(FestivalContentTranslate): [title={}, lang={}]", entity.getTitle(), entity.getLanguage());
  }

  @Override
  public void deleteFestivalContentTranslate(Long contentId, TranslateLanguage language) {
    String esId = contentId + "_" + language;

    // Elasticsearch 삭제
    contentTranslateDocumentRepository.deleteById(esId);
    log.info("Elasticsearch Document 삭제 완료(FestivalContentTranslate) : esId={}", esId);
  }
}
