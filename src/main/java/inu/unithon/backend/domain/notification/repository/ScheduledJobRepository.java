package inu.unithon.backend.domain.notification.repository;

import inu.unithon.backend.domain.notification.entity.FestivalNotificationType;
import inu.unithon.backend.domain.notification.entity.ScheduledJob;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ScheduledJobRepository extends JpaRepository<ScheduledJob, Long> {

  Optional<ScheduledJob> findByUserIdAndFestivalId(Long userId, Long festivalId);
  boolean existsByUserIdAndFestivalIdAndType(Long userId, Long festivalId, FestivalNotificationType type);
}
