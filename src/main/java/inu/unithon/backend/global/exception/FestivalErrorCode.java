package inu.unithon.backend.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum FestivalErrorCode implements ErrorCode {
  FESTIVAL_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 축제를 찾을 수 없습니다."),
  FESTIVAL_DB_SEARCH_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Festival DB Search error");

  private final HttpStatus status;
  private final String message;

  FestivalErrorCode(HttpStatus status, String message) {
    this.status = status;
    this.message = message;
  }
}
