package inu.unithon.backend.domain.notification.policy;

import inu.unithon.backend.domain.notification.entity.FestivalNotificationType;

import java.time.LocalDateTime;

/**
 * 알림 시점 계산하는 전략 공통 인터페이스
 */
public interface NotificationTimingPolicy {
  FestivalNotificationType getType(); // START or END
  LocalDateTime calculateExecuteAt(LocalDateTime targetDate);
}

