package inu.unithon.backend.domain.notification.controller;

import inu.unithon.backend.domain.notification.entity.ScheduledJob;
import inu.unithon.backend.global.scheduler.QuartzService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/test")
public class NotificationTestController {

  private final QuartzService quartzService;

  @GetMapping("/set-notifi")
  public void setNotification() {
    ScheduledJob job = ScheduledJob.builder()
      .userId(1L)
      .message("test scheduled job")
      .festivalId(1L)
      .executeAt(LocalDateTime.now().plusSeconds(30))
      .build();
    quartzService.createAndScheduleJob(job);
  }

  @GetMapping("/del-notifi")
  public void deleteNotification() {
    quartzService.deleteScheduleJob(1L, 1L);
  }
}
