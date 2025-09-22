package inu.unithon.backend.domain.festival.repository.festivalTranslate;

import inu.unithon.backend.domain.festival.entity.FestivalTranslate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FestivalTranslateRepository extends JpaRepository<FestivalTranslate, Long>, FestivalTranslateRepositoryCustom {
}
