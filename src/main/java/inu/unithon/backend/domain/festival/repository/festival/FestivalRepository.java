package inu.unithon.backend.domain.festival.repository.festival;

import inu.unithon.backend.domain.festival.entity.Festival;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FestivalRepository extends JpaRepository<Festival, Long>, FestivalRepositoryCustom {
}
