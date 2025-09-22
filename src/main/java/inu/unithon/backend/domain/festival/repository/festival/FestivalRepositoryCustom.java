package inu.unithon.backend.domain.festival.repository.festival;

import inu.unithon.backend.domain.festival.entity.Festival;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

/**
 * 커스텀 쿼리 메서드
 * JPA 기본 메서드 외에, 복잡한 검색 조건이 있는 쿼리
 */
public interface FestivalRepositoryCustom {
  // keyword 검색
  Page<Festival> findByKeyword(String keyword, Pageable pageable);

  // 기간별 검색
  Page<Festival> findFestivalsByPeriod(LocalDateTime start, LocalDateTime end, Pageable pageable);
}
