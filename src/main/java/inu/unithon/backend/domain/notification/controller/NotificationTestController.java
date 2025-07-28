package inu.unithon.backend.domain.notification.controller;

import inu.unithon.backend.domain.member.entity.CustomUserDetails;
import inu.unithon.backend.domain.notification.service.reservation.NotificationReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/test")
public class NotificationTestController {

  private final NotificationReservationService notificationReservationService;

  /**
   * 사용자가 좋아요 누름 ->
   * 알림 예약
   */
  @GetMapping("/set-notifi/{festivalId}")
  public void setNotification(@AuthenticationPrincipal CustomUserDetails userDetails,
                              @PathVariable Long festivalId) {

    notificationReservationService.reserveAllNotifications(userDetails.getId(), festivalId);

  }

  @GetMapping("/del-notifi/{festivalId}")
  public void deleteNotification(@AuthenticationPrincipal CustomUserDetails userDetails,
                                 @PathVariable Long festivalId) {
    notificationReservationService.deleteAllNotifications(userDetails.getId(), festivalId);
  }
}
