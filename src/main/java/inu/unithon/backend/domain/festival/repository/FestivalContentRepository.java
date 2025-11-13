package inu.unithon.backend.domain.festival.repository;

import inu.unithon.backend.domain.festival.entity.FestivalContent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FestivalContentRepository extends JpaRepository<FestivalContent, Long> {
  Optional<FestivalContent> findByContentId(long contentId);
}
