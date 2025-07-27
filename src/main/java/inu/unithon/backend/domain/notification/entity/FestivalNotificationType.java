package inu.unithon.backend.domain.notification.entity;

import inu.unithon.backend.global.email.EmailService;
import inu.unithon.backend.global.email.dto.FestivalEmailDto;

/**
 * 축제 알림 종류 Enum class
 * Start / End
 * @author Frozzun
 */
public enum FestivalNotificationType {

  START {
    @Override
    public void send(EmailService service, FestivalEmailDto dto) {
      service.sendFestivalStartEmail(dto);
    }
  },
  END {
    @Override
    public void send(EmailService service, FestivalEmailDto dto) {
      service.sendFestivalEndEmail(dto);
    }
  };

  public abstract void send(EmailService service, FestivalEmailDto dto);

}
