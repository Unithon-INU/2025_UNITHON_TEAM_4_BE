package inu.unithon.backend.domain.festival.repository;

import inu.unithon.backend.domain.festival.entity.FestivalContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface FestivalContentRepository extends JpaRepository<FestivalContent, Long> {
    Optional<FestivalContent> findByContentId(long contentId);
}
