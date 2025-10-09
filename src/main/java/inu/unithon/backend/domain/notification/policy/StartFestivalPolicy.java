package inu.unithon.backend.domain.notification.policy;

import inu.unithon.backend.domain.notification.entity.FestivalNotificationType;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class StartFestivalPolicy implements NotificationTimingPolicy {

  private final int daysBefore = 3; // TODO: Configurable

  @Override
  public FestivalNotificationType getType() {
    return FestivalNotificationType.START;
  }

  @Override
  public LocalDateTime calculateExecuteAt(LocalDateTime startDate) {
    return startDate.toLocalDate().minusDays(daysBefore).atTime(10, 0);
  }
}

