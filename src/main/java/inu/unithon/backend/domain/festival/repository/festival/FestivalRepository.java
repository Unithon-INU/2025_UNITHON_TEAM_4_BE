package inu.unithon.backend.domain.festival.repository.festival;

import inu.unithon.backend.domain.festival.entity.Festival;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.data.domain.Pageable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface FestivalRepository extends JpaRepository<Festival, Long>, FestivalRepositoryCustom {
  Optional<Festival> findByContentId(long contentId);

  @Query("SELECT f.contentId FROM Festival f WHERE f.contentId IN :contentIds")
  List<Long> findContentIdsByContentIds(@Param("contentIds") List<Long> contentIds);

  @Query("""
    select f
    from Festival f
    where (:start is null or f.endDate >= :start)
      and (:end   is null or f.startDate <= :end)
    """)
  Page<Festival> findAllOverlapping(
          @Param("start") LocalDateTime start,
          @Param("end") LocalDateTime end,
          Pageable pageable
  );

}
