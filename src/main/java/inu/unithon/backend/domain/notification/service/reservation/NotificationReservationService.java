package inu.unithon.backend.domain.notification.service.reservation;

import inu.unithon.backend.domain.notification.entity.FestivalNotificationType;

public interface NotificationReservationService {

  void reserveAllNotifications(Long userId, Long festivalId);

  void deleteAllNotifications(Long userId, Long festivalId);
}
