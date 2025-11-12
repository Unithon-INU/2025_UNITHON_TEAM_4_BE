package inu.unithon.backend.domain.translate.repository.sql.festivalTranslate;

import inu.unithon.backend.domain.translate.entity.FestivalTranslate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FestivalTranslateRepository extends JpaRepository<FestivalTranslate, Long>, FestivalTranslateRepositoryCustom {
}
