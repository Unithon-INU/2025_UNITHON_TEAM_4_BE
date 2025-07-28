package inu.unithon.backend.domain.festival.repository;

import inu.unithon.backend.domain.festival.entity.Festival;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FestivalRepository extends JpaRepository<Festival, Long> {
}
