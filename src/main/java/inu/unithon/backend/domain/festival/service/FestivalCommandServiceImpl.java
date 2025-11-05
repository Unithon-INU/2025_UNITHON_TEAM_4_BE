package inu.unithon.backend.domain.festival.service;

import inu.unithon.backend.domain.festival.document.FestivalTranslateDocument;
import inu.unithon.backend.domain.festival.entity.FestivalTranslate;
import inu.unithon.backend.domain.festival.mapper.FestivalMapper;
import inu.unithon.backend.domain.festival.repository.festivalTranslate.es.FestivalTranslateDocumentRepository;
import inu.unithon.backend.domain.festival.repository.festivalTranslate.sql.FestivalTranslateRepository;
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

  private final FestivalTranslateRepository jpaRepository;
  private final FestivalTranslateDocumentRepository esRepository;
  private final FestivalMapper mapper;

  /**
   * DB insert 시 ES 자동 인덱싱
   */
  @Override
  public void createFestivalTranslate(FestivalTranslate entity) {
    FestivalTranslate saved = jpaRepository.save(entity);
    FestivalTranslateDocument doc = mapper.toDocumentFromFestivalTranslate(saved);
    esRepository.save(doc);

    log.info("Elasticsearch 인덱싱 완료: [title={}, lang={}]", saved.getTitle(), saved.getLanguage());
  }

  /**
   * DB + ES 동시 삭제
   */
  @Override
  public void deleteFestivalTranslate(Long id) {
    // DB에서 FestivalTranslate 조회
    FestivalTranslate festivalTranslate = jpaRepository.findById(id)
      .orElseThrow(() -> new CustomException(FestivalErrorCode.FESTIVAL_NOT_FOUND));

    // DB 삭제
    jpaRepository.deleteById(id);
    log.info("🗑DB FestivalTranslate 삭제 완료: id={}", id);

    // ES 문서 ID 생성 (festivalId_language 형식)
    Long festivalId = festivalTranslate.getFestival().getId();
    String language = festivalTranslate.getLanguage().name();
    String esId = festivalId + "_" + language;

    //⃣ Elasticsearch 삭제
    esRepository.deleteById(esId);
    log.info("🗑Elasticsearch Document 삭제 완료: esId={}", esId);
  }
}
