package inu.unithon.backend.domain.festival.repository;

import inu.unithon.backend.domain.festival.entity.Festival;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface FestivalRepository extends JpaRepository<Festival, Long>{
//    boolean existsByContentId(long contentId);
    Optional<Festival> findByContentId(long contentId);

    @Query("SELECT f.contentId FROM Festival f WHERE f.contentId IN :contentIds")
    List<Long> findContentIdsByContentIds(@Param("contentId") List<Long> contentIds);

}
