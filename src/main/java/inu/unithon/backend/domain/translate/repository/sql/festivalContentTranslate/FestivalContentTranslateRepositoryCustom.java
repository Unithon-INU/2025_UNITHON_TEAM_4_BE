package inu.unithon.backend.domain.translate.repository.sql.festivalContentTranslate;

import inu.unithon.backend.domain.translate.entity.TranslateLanguage;

/**
 * 커스텀 쿼리 메서드
 * JPA 기본 메서드 외에, 복잡한 검색 조건이 있는 쿼리
 */
public interface FestivalContentTranslateRepositoryCustom {

  boolean existsByContentIdAndLanguage(Long contentId, TranslateLanguage language);
  void deleteByContentIdAndLanguage(Long contentId, TranslateLanguage language);
}
