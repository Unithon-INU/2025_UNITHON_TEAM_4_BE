package inu.unithon.backend.domain.festival.repository.festival;

import inu.unithon.backend.domain.festival.entity.Festival;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FestivalRepository extends JpaRepository<Festival, Long>, FestivalRepositoryCustom {
  Optional<Festival> findByContentId(long contentId);

  @Query("SELECT f.contentId FROM Festival f WHERE f.contentId IN :contentIds")
  List<Long> findContentIdsByContentIds(@Param("contentId") List<Long> contentIds);

}
