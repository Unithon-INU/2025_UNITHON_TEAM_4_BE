package inu.unithon.backend.domain.festival.repository.festivalTranslate.es;

import inu.unithon.backend.domain.festival.document.FestivalTranslateDocument;
import inu.unithon.backend.domain.festival.entity.TranslateLanguage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

/**
 * 커스텀 쿼리 메서드
 * ElasticSearch 복잡한 검색 조건이 있는 쿼리
 */
public interface FestivalTranslateDocumentRepositoryCustom {

  Page<FestivalTranslateDocument> searchByKeyword(TranslateLanguage language, String keyword, Pageable pageable);

  Page<FestivalTranslateDocument> searchByPeriod(TranslateLanguage language, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);
}
