package inu.unithon.backend.domain.translate.repository.sql.festivalTranslate;

import inu.unithon.backend.domain.translate.entity.FestivalTranslate;
import inu.unithon.backend.domain.translate.entity.TranslateLanguage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

/**
 * 커스텀 쿼리 메서드
 * JPA 기본 메서드 외에, 복잡한 검색 조건이 있는 쿼리
 */
public interface FestivalTranslateRepositoryCustom {

  /**
   * keyword 검색
   * @param language : 사용자가 web page 에서 설정한 언어. -> client 에서 받아옴
   * @param keyword : 사용자가 입력한 keyword
   * @param pageable
   * @return Page<FestivalTranslate>
   */
  Page<FestivalTranslate> findByKeyword(TranslateLanguage language, String keyword, Pageable pageable);

  /**
   * 기간별 축제 검색
   * @param language : 사용자가 web page 에서 설정한 언어. -> client 에서 받아옴
   * @param startDate : 사용자가 설정한 축제 시작 일
   * @param endDate : 사용자가 설정한 축제 종료 일
   * @param pageable
   * @return Page<FestivalTranslate>
   */
  Page<FestivalTranslate> findFestivalsByPeriod(TranslateLanguage language, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

  /**
   * 언어별 페이징 조회 (축제 목록)
   * @param language
   * @param pageable
   * @return
   */
  Page<FestivalTranslate> findByLanguage(TranslateLanguage language, Pageable pageable);

  boolean existsByContentIdAndLanguage(Long contentId, TranslateLanguage language);
  void deleteByContentIdAndLanguage(Long contentId, TranslateLanguage language);
}
