package inu.unithon.backend.domain.notification.entity;

import java.time.LocalDateTime;

public enum FestivalStatus {
  PRE,     // 시작 전
  ACTIVE,  // 진행 중
  ENDED;   // 종료됨

  public static FestivalStatus from(LocalDateTime now, LocalDateTime start, LocalDateTime end) {
    if (now.isBefore(start)) {
      return PRE;
    } else if (now.isBefore(end)) {
      return ACTIVE;
    } else {
      return ENDED;
    }
  }
}

