package inu.unithon.backend.domain.festival.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import inu.unithon.backend.domain.festival.entity.Festival;
import inu.unithon.backend.domain.festival.entity.QFestival;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
@RequiredArgsConstructor
public class FestivalRepositoryImpl implements FestivalRepositoryCustom {

  private final JPAQueryFactory queryFactory;
  private final QFestival festival = QFestival.festival;

  @Override
  public Page<Festival> findByKeyword(String keyword, Pageable pageable) {
    return null;
  }

  @Override
  public Page<Festival> findFestivalsByPeriod(LocalDateTime start, LocalDateTime end, Pageable pageable) {
    return null;
  }
}
