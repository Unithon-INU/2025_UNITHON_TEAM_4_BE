package inu.unithon.backend.global.exception;

import lombok.Getter;

import java.time.LocalDateTime;

/**
 *
 * 모든 커스텀 예외의 상위 클래스
 */
@Getter
public class CustomException extends RuntimeException {
  private final ErrorCode errorCode;
  private final LocalDateTime timestamp;

  public CustomException(ErrorCode errorCode) {
    super(errorCode.getMessage()); // 메시지는 자동 설정
    this.errorCode = errorCode;
    this.timestamp = LocalDateTime.now();
  }
}
