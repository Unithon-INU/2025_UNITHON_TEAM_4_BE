package inu.unithon.backend.domain.notification.entity;

import inu.unithon.backend.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ScheduledJob extends BaseEntity {

  @Id
  @GeneratedValue
  private Long id;

  private Long userId;
  private Long festivalId;

  // 실행 날짜
  private LocalDateTime executeAt;

  // 실행 되었는가 true/false
  @Setter
  private boolean executed;

  @Enumerated(EnumType.STRING)
  private FestivalNotificationType type;

  @Builder
  public ScheduledJob(Long userId, Long festivalId, LocalDateTime executeAt, FestivalNotificationType type) {
    this.userId = userId;
    this.festivalId = festivalId;
    this.executeAt = executeAt;
    this.type = type;
  }

}

