package inu.unithon.backend.domain.notification.policy;

import inu.unithon.backend.domain.notification.entity.FestivalNotificationType;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class EndFestivalPolicy implements NotificationTimingPolicy {

  private final int daysBefore = 1;

  @Override
  public FestivalNotificationType getType() {
    return FestivalNotificationType.END;
  }

  @Override
  public LocalDateTime calculateExecuteAt(LocalDateTime endDate) {
    return endDate.toLocalDate().minusDays(daysBefore).atTime(10, 0);
  }
}

