package inu.unithon.backend.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 *
 * 에러 코드 관리
 */
@Getter
public enum CommonErrorCode implements ErrorCode {
  INVALID_INPUT(HttpStatus.BAD_REQUEST, "유효성 검사 실패"),
  FORBIDDEN(HttpStatus.UNAUTHORIZED, "삭제 할 권한이 없습니다."),
  UNAUTHORIZED_400(HttpStatus.UNAUTHORIZED, "계정 비활성화"),
  UNAUTHORIZED_401(HttpStatus.UNAUTHORIZED, "인증 실패"),
  INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다."),
  SAME_PASSWORD(HttpStatus.BAD_REQUEST, "기존의 비밀번호와 달라야 합니다."),
  POST_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 게시글을 찾을 수 없습니다."),

  NO_IMAGE_INPUT(HttpStatus.BAD_REQUEST, "적어도 하나의 이미지를 업로드 해야됩니다."),
  S3_UPLOAD_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "S3 이미지 업로드에 실패했습니다."),
  S3_DELETE_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "S3 이미지 삭제에 실패했습니다."),
  S3_INVALID_URL(HttpStatus.BAD_REQUEST, "유효하지 않은 S3 URL 입니다."),
  PAYLOAD_TOO_LARGE(HttpStatus.PAYLOAD_TOO_LARGE, "파일 크기가 너무 큽니다."),

  COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 댓글을 찾을 수 없습니다."),
  FORBIDDEN_401(HttpStatus.UNAUTHORIZED, "본인의 댓글만 수정 할 수 있습니다."),
  FORBIDDEN_402(HttpStatus.UNAUTHORIZED, "본인의 댓글만 삭제 할 수 있습니다."),

  JOB_SCHEDULING_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "알림 Job 스케줄링에 실패했습니다."),
  JOB_EXECUTION_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "알림 Job 실행 중 오류가 발생했습니다."),
  JOB_NOT_FOUND(HttpStatus.NOT_FOUND, "알림 Job 존재하지 않습니다."),
  JOB_DELETE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "알림 Job 삭제 실패."),

  EMAIL_SEND_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "이메일 전송 실패."),

  FESTIVAL_NOT_FOUND(HttpStatus.INTERNAL_SERVER_ERROR, "존재하지 않는 축제입니다. "),

  INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류가 발생했습니다.");

  private final HttpStatus status;
  private final String message;

  CommonErrorCode(HttpStatus status, String message) {
    this.status = status;
    this.message = message;
  }
}
