package inu.unithon.backend.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum NotificationErrorCode implements ErrorCode {
  JOB_SCHEDULING_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "알림 Job 스케줄링에 실패했습니다."),
  JOB_EXECUTION_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "알림 Job 실행 중 오류가 발생했습니다."),
  JOB_NOT_FOUND(HttpStatus.NOT_FOUND, "알림 Job 존재하지 않습니다."),
  JOB_DELETE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "알림 Job 삭제 실패."),
  EMAIL_SEND_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "이메일 전송 실패.");

  private final HttpStatus status;
  private final String message;

  NotificationErrorCode(HttpStatus status, String message) {
    this.status = status;
    this.message = message;
  }
}
