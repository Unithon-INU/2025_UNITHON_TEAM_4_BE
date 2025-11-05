package inu.unithon.backend.domain.festival.repository.festivalTranslate.sql;

import com.querydsl.jpa.impl.JPAQueryFactory;
import inu.unithon.backend.domain.festival.entity.FestivalTranslate;
import inu.unithon.backend.domain.festival.entity.QFestivalTranslate;
import inu.unithon.backend.domain.festival.entity.TranslateLanguage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class FestivalTranslateRepositoryImpl implements FestivalTranslateRepositoryCustom {

  private final JPAQueryFactory queryFactory;
  private final QFestivalTranslate festivalTranslate = QFestivalTranslate.festivalTranslate;

  @Override
  public Page<FestivalTranslate> findByKeyword(TranslateLanguage language, String keyword, Pageable pageable) {
    List<FestivalTranslate> content = queryFactory
      .selectFrom(festivalTranslate)
      .where(
        festivalTranslate.language.eq(language)
          .and(festivalTranslate.title.containsIgnoreCase(keyword)
          .or(festivalTranslate.address.containsIgnoreCase(keyword))
          .or(festivalTranslate.content.containsIgnoreCase(keyword)))
      )
      .offset(pageable.getOffset())
      .limit(pageable.getPageSize())
      .fetch();

    Long count = queryFactory
      .select(festivalTranslate.count())
      .from(festivalTranslate)
      .where(
        festivalTranslate.title.containsIgnoreCase(keyword)
          .or(festivalTranslate.address.containsIgnoreCase(keyword))
          .or(festivalTranslate.content.containsIgnoreCase(keyword))
      )
      .fetchOne();

    return new PageImpl<>(content, pageable, count != null ? count : 0);
  }

  @Override
  public Page<FestivalTranslate> findFestivalsByPeriod(TranslateLanguage language, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable) {
    List<FestivalTranslate> content = queryFactory
      .selectFrom(festivalTranslate)
      .where(
        festivalTranslate.language.eq(language)
          .and(festivalTranslate.startDate.goe(startDate)
          .and(festivalTranslate.endDate.loe(endDate)))
      )
      .offset(pageable.getOffset())
      .limit(pageable.getPageSize())
      .fetch();

    Long count = queryFactory
      .select(festivalTranslate.count())
      .from(festivalTranslate)
      .where(
        festivalTranslate.startDate.goe(startDate)
          .and(festivalTranslate.endDate.loe(endDate))
      )
      .fetchOne();
    return new PageImpl<>(content, pageable, count != null ? count : 0);
  }

  /**
   * 언어별 페이징 조회 (축제 목록)
   *
   * @param language
   * @param pageable
   * @return
   */
  @Override
  public Page<FestivalTranslate> findByLanguage(TranslateLanguage language, Pageable pageable) {
    List<FestivalTranslate> content = queryFactory
      .selectFrom(festivalTranslate)
      .where(festivalTranslate.language.eq(language))
      .offset(pageable.getOffset())
      .limit(pageable.getPageSize())
      .fetch();

    Long count = queryFactory
      .select(festivalTranslate.count())
      .from(festivalTranslate)
      .where(festivalTranslate.language.eq(language))
      .fetchOne();

    return new PageImpl<>(content, pageable, count != null ? count : 0);
  }
}
