package inu.unithon.backend.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum UserErrorCode implements ErrorCode{
  USER_NOT_FOUND(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."),
  EMAIL_DUPLICATED(HttpStatus.CONFLICT, "이미 등록된 이메일입니다."),
  NAME_DUPLICATED(HttpStatus.CONFLICT, "이미 등록된 이름입니다."),
  INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다."),
  SAME_PASSWORD(HttpStatus.BAD_REQUEST, "기존의 비밀번호와 달라야 합니다."),
  UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "아이디 또는 비밀번호가 일치하지 않습니다.");

  private final HttpStatus status;
  private final String message;

  UserErrorCode(HttpStatus status, String message) {
    this.status = status;
    this.message = message;
  }
}

