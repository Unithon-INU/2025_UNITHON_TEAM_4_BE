package inu.unithon.backend.domain.translate.repository.sql.festivalContentTranslate;

import inu.unithon.backend.domain.translate.entity.FestivalContentTranslate;
import inu.unithon.backend.domain.translate.entity.TranslateLanguage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FestivalContentTranslateRepository extends JpaRepository<FestivalContentTranslate, Long>, FestivalContentTranslateRepositoryCustom {
  /**
   * 특정 contentId + 언어로 전체 조회
   *   - 상세 정보 리스트 등에서 사용
   */
  List<FestivalContentTranslate> findByContentIdAndLanguage(Long contentId, TranslateLanguage language);


  /**
   * 특정 contentId + 언어로 단건 조회
   *   - intro/info 상세 페이지용
   */
  Optional<FestivalContentTranslate> findFirstByContentIdAndLanguage(Long contentId, TranslateLanguage language);

}
