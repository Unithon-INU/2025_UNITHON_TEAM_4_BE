package inu.unithon.backend.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum TranslateErrorCode {
  TRANSLATE_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "번역 서버 오류.");

  private final HttpStatus status;
  private final String message;

  TranslateErrorCode(HttpStatus status, String message) {
    this.status = status;
    this.message = message;
  }
}
