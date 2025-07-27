package inu.unithon.backend.domain.festival.repository;

import inu.unithon.backend.domain.festival.entity.FestivalContent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FestivalContentRepository extends JpaRepository<FestivalContent, Long> {
}
