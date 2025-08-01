package inu.unithon.backend.domain.notification.service.reservation;

import inu.unithon.backend.domain.festival.entity.Festival;
import inu.unithon.backend.domain.festival.service.FestivalService;
import inu.unithon.backend.domain.notification.entity.FestivalNotificationType;
import inu.unithon.backend.domain.notification.entity.ScheduledJob;
import inu.unithon.backend.domain.notification.policy.NotificationTimingPolicy;
import inu.unithon.backend.global.scheduler.QuartzService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static inu.unithon.backend.domain.notification.entity.FestivalNotificationType.START;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationReservationServiceImpl implements NotificationReservationService {

  private final FestivalService festivalService;
  private final QuartzService quartzService;
  private final List<NotificationTimingPolicy> timingPolicies;

  @Override
  public void reserveAllNotifications(Long userId, Long festivalId) {

    Festival festival = festivalService.getFestival(festivalId);

    for (NotificationTimingPolicy policy : timingPolicies) {  // 모든 알림 정책 순회
      FestivalNotificationType type = policy.getType();

      // 중복 예약 방지
      boolean alreadyExists = quartzService.existsJob(userId, festivalId, type);
      if (alreadyExists) {
        log.info("이미 예약된 알림입니다. userId={}, festivalId={}, type={}", userId, festivalId, type);
        continue;
      }

      LocalDateTime targetDate = policy.getType() == START
        ? festival.getStartDate()
        : festival.getEndDate();

      LocalDateTime executeAt = policy.calculateExecuteAt(targetDate);

      ScheduledJob job = ScheduledJob.builder()
        .userId(userId)
        .festivalId(festivalId)
        .type(policy.getType())
        .executeAt(executeAt)
        .build();

      try {
        quartzService.createAndScheduleJob(job);
      } catch (DataIntegrityViolationException e) {
        // 동시성으로 인해 존재하지 않던 job이 누군가에 의해 먼저 save된 경우
        log.warn("중복 예약 충돌 감지 (DB 유니크 인덱스 방어 성공): userId={}, festivalId={}, type={}", userId, festivalId, policy.getType());
      }
    }
  }

  @Override
  public void deleteAllNotifications(Long userId, Long festivalId) {
    quartzService.deleteScheduleJob(userId, festivalId);
  }
}
