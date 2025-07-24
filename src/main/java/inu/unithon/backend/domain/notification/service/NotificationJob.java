package inu.unithon.backend.domain.notification.service;

import inu.unithon.backend.domain.notification.repository.ScheduledJobRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationJob implements Job {

  private final ScheduledJobRepository scheduledJobRepository;

  // 알림을 보내는 타이밍(트리거)에 execute 메서드 실행됨.
  @Override
  public void execute(JobExecutionContext context) {

    // 전송 처리(로깅)
    String msg = context.getMergedJobDataMap().getString("message");
    String userId = context.getMergedJobDataMap().getString("userId");
    log.info("notification_event: {{\"userId\": \"{}\", \"message\": \"{}\"}}", userId, msg);

    // 실행 상태 업데이트
    Long jobId = Long.valueOf(context.getJobDetail().getKey().getName().split("_")[1]);
    scheduledJobRepository.findById(jobId).ifPresent(job -> {
      job.setExecuted(true);
      scheduledJobRepository.save(job);
    });
  }
}

