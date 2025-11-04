package inu.unithon.backend.domain.festival.repository.festivalTranslate.sql;

import inu.unithon.backend.domain.festival.entity.FestivalTranslate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FestivalTranslateRepository extends JpaRepository<FestivalTranslate, Long>, FestivalTranslateRepositoryCustom {
}
