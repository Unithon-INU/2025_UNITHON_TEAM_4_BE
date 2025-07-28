package inu.unithon.backend.global.email;

import inu.unithon.backend.global.email.dto.FestivalEmailDto;

public interface EmailService {

  /**
   * 축제 시작 알림 이메일 전송
   * @param dto
   */
  void sendFestivalStartEmail(FestivalEmailDto dto);

  /**
   * 축제 종료 알림 이메일 전송
   * @param dto
   */
  void sendFestivalEndEmail(FestivalEmailDto dto);
}
