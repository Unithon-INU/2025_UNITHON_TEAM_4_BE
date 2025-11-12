package inu.unithon.backend.domain.translate.repository.sql.festivalContentTranslate;

import com.querydsl.jpa.impl.JPAQueryFactory;
import inu.unithon.backend.domain.translate.entity.QFestivalContentTranslate;
import inu.unithon.backend.domain.translate.entity.TranslateLanguage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class FestivalContentTranslateRepositoryImpl implements FestivalContentTranslateRepositoryCustom {

  private final JPAQueryFactory queryFactory;
  private final QFestivalContentTranslate contentTranslate = QFestivalContentTranslate.festivalContentTranslate;

  /**
   * contentId + language 로 존재 여부 확인
   */
  @Override
  public boolean existsByContentIdAndLanguage(Long contentId, TranslateLanguage language) {
    Integer result = queryFactory
      .selectOne()
      .from(contentTranslate)
      .where(
        contentTranslate.contentId.eq(contentId)
          .and(contentTranslate.language.eq(language))
      )
      .fetchFirst(); // limit 1

    return result != null;
  }

  /**
   * contentId + language 로 삭제
   */
  @Override
  public void deleteByContentIdAndLanguage(Long contentId, TranslateLanguage language) {
    queryFactory
      .delete(contentTranslate)
      .where(
        contentTranslate.contentId.eq(contentId)
          .and(contentTranslate.language.eq(language))
      )
      .execute();
  }
}
