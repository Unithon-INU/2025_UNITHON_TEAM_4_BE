package inu.unithon.backend.domain.notification.service.reservation;

import inu.unithon.backend.domain.festival.entity.Festival;
import inu.unithon.backend.domain.festival.service.FestivalService;
import inu.unithon.backend.domain.notification.entity.FestivalNotificationType;
import inu.unithon.backend.domain.notification.entity.FestivalStatus;
import inu.unithon.backend.domain.notification.entity.ScheduledJob;
import inu.unithon.backend.domain.notification.policy.NotificationTimingPolicy;
import inu.unithon.backend.global.scheduler.QuartzService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static inu.unithon.backend.domain.notification.entity.FestivalNotificationType.END;
import static inu.unithon.backend.domain.notification.entity.FestivalNotificationType.START;
import static inu.unithon.backend.domain.notification.entity.FestivalStatus.ENDED;
import static inu.unithon.backend.domain.notification.entity.FestivalStatus.PRE;

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
    LocalDateTime now = LocalDateTime.now();
    LocalDateTime startDate = festival.getStartDate();
    LocalDateTime endDate = festival.getEndDate();

    FestivalStatus status = FestivalStatus.from(now, startDate, endDate);

    for (NotificationTimingPolicy policy : timingPolicies) {  // 모든 알림 정책 순회
      FestivalNotificationType type = policy.getType();

      // 축제 상태에 따라 필터링
      if (status == FestivalStatus.ACTIVE) {
        if (type == START) {
          log.info("시작 알림 생략: 이미 시작된 축제");
          continue;
        }
        // END는 가능
      } else if (status == FestivalStatus.ENDED) {
        log.info("모든 알림 생략: 이미 종료된 축제");
        continue;
      }

      // 중복 예약 방지
      if (quartzService.existsJob(userId, festivalId, type)) {
        log.info("이미 예약된 알림입니다. userId={}, festivalId={}, type={}", userId, festivalId, type);
        continue;
      }

      LocalDateTime targetDate = (type == START) ? startDate : endDate;
      LocalDateTime executeAt = policy.calculateExecuteAt(targetDate);

      // 과거 시점 예약 생략
      if (executeAt.isBefore(now)) {
        log.info("과거 시점 알림 생략: executeAt={}", executeAt);
        continue;
      }

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
