package inu.unithon.backend.domain.notification.entity;

import inu.unithon.backend.global.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
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
  private String message;

  // 실행 날짜
  private LocalDateTime executeAt;

  @Setter
  private boolean executed;

  @Builder
  public ScheduledJob(Long userId, Long festivalId, String message, LocalDateTime executeAt) {
    this.userId = userId;
    this.festivalId = festivalId;
    this.message = message;
    this.executeAt = executeAt;
  }

}

