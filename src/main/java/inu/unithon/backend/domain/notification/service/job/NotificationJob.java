package inu.unithon.backend.domain.notification.service.job;

import inu.unithon.backend.domain.festival.entity.Festival;
import inu.unithon.backend.domain.festival.service.FestivalServiceImpl;
import inu.unithon.backend.domain.member.entity.Member;
import inu.unithon.backend.domain.member.service.MemberService;
import inu.unithon.backend.domain.notification.entity.FestivalNotificationType;
import inu.unithon.backend.domain.notification.repository.ScheduledJobRepository;
import inu.unithon.backend.global.email.service.EmailService;
import inu.unithon.backend.global.email.dto.FestivalEmailDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


/**
 * 
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationJob implements Job {

  private final ScheduledJobRepository scheduledJobRepository;
  private final EmailService emailService;
  private final MemberService memberService;
  private final FestivalServiceImpl festivalServiceImpl;

  // 알림을 보내는 타이밍(트리거)에 execute 메서드 실행됨.
  @Override
  @Transactional
  public void execute(JobExecutionContext context) {
    JobDataMap dataMap = context.getMergedJobDataMap();

    Long realUserId = Long.parseLong(dataMap.getString("userId"));
    Long realFestivalId = Long.parseLong(dataMap.getString("festivalId"));
    String executeAt = dataMap.getString("executeAt");

    // 수정된 부분: type은 int로 꺼낸 후 enum으로 변환
    int typeOrdinal = (int) dataMap.get("type");
    FestivalNotificationType type = FestivalNotificationType.values()[typeOrdinal];  // 예: 알림 종류 enum

    log.info("notification_event: {{ " +
      "\"userId\": \"{}\", " +
      "\"festivalId\": \"{}\", " +
      "\"executeAt\": \"{}\", " +
      "\"type\": \"{}\" }}", realUserId, realFestivalId, executeAt, type);

    Member member = memberService.getMember(realUserId);
    Festival festival = festivalServiceImpl.getFestival(realFestivalId);

    FestivalEmailDto dto = FestivalEmailDto.builder()
      .email(member.getEmail())
      .subject("asdf")  // TODO: 수정해야됨.
      .festivalName(festival.getTitle())
      .festivalStart(festival.getStartDate())
      .festivalEnd(festival.getEndDate())
      .build();

    type.send(emailService, dto);

    // 실행 상태 업데이트
    Long jobId = Long.valueOf(context.getJobDetail().getKey().getName().split("_")[1]);
    scheduledJobRepository.findById(jobId).ifPresent(job -> {
      job.setExecuted(true);
      scheduledJobRepository.save(job);
    });
  }

}

