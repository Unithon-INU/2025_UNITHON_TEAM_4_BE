package inu.unithon.backend.global.scheduler;

import inu.unithon.backend.domain.notification.entity.FestivalNotificationType;
import inu.unithon.backend.domain.notification.entity.ScheduledJob;
import inu.unithon.backend.domain.notification.repository.ScheduledJobRepository;
import inu.unithon.backend.domain.notification.service.job.NotificationJob;
import inu.unithon.backend.global.exception.CustomException;
import inu.unithon.backend.global.exception.CommonErrorCode;
import lombok.RequiredArgsConstructor;
import org.quartz.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;


@Service
@Transactional
@RequiredArgsConstructor
public class QuartzService {

  private final Scheduler scheduler;
  private final ScheduledJobRepository scheduledJobRepository;

  public void createAndScheduleJob(ScheduledJob jobEntity) {

    // 저장
    scheduledJobRepository.save(jobEntity);

    // Quartz 등록
    JobDetail jobDetail = JobBuilder.newJob(NotificationJob.class)
      .withIdentity("job_" + jobEntity.getId(), "custom-jobs")
      .usingJobData("userId", String.valueOf(jobEntity.getUserId()))
      .usingJobData("festivalId", String.valueOf(jobEntity.getFestivalId()))
      .usingJobData("executeAt", String.valueOf(jobEntity.getExecuteAt()))
      .usingJobData("type", jobEntity.getType().ordinal())
      .build();

    Trigger trigger = TriggerBuilder.newTrigger()
      .withIdentity("trigger_" + jobEntity.getId(), "custom-triggers")
      .startAt(Date.from(jobEntity.getExecuteAt().atZone(ZoneId.systemDefault()).toInstant()))
      .build();
    try {
      scheduler.scheduleJob(jobDetail, trigger);
    } catch (SchedulerException e) {
      throw new CustomException(CommonErrorCode.JOB_SCHEDULING_FAILED);
    }
  }

  public void deleteScheduleJob(Long userId, Long festivalId) {
    Optional<ScheduledJob> optionalJob = scheduledJobRepository.findByUserIdAndFestivalId(userId, festivalId);

    ScheduledJob job = optionalJob.orElseThrow(() ->
      new CustomException(CommonErrorCode.JOB_NOT_FOUND)
    );

    // 2. Quartz Job 삭제
    JobKey jobKey = new JobKey("job_" + job.getId(), "custom-jobs");
    try {
      boolean deleted = scheduler.deleteJob(jobKey);
      if (!deleted) {
        throw new CustomException(CommonErrorCode.JOB_DELETE_FAILED);
      }
    } catch (SchedulerException e) {
      throw new CustomException(CommonErrorCode.JOB_DELETE_FAILED);
    }

    // 3. DB에서도 제거
    scheduledJobRepository.delete(job);
  }

  // 중복 확인 메서드
  public boolean existsJob(Long userId, Long festivalId, FestivalNotificationType type) {
    return scheduledJobRepository.existsByUserIdAndFestivalIdAndType(userId, festivalId, type);
  }
}

