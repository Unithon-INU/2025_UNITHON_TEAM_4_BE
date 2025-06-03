package inu.unithon.backend.global.exception;

import inu.unithon.backend.global.response.ResponseDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

  // 유효성 검사 실패 처리
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ResponseDto<?>> handleValidationException(MethodArgumentNotValidException ex) {
    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult().getFieldErrors()
      .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

    return ResponseEntity
      .status(ErrorCode.INVALID_INPUT.getStatus())
      .body(ResponseDto.error(
        ErrorCode.INVALID_INPUT.getStatus().value(),
        ErrorCode.INVALID_INPUT.getMessage(),
        errors
        ));
  }

  @ExceptionHandler(BadCredentialsException.class)
  public ResponseEntity<ResponseDto<?>> handleBadCredentials(BadCredentialsException ex) {
    return ResponseEntity
      .status(401)
      .body(ResponseDto.error(
        401,
        "아이디 또는 비밀번호가 잘못되었습니다."
      ));
  }

  @ExceptionHandler(UsernameNotFoundException.class)
  public ResponseEntity<ResponseDto<?>> handleUsernameNotFound(UsernameNotFoundException ex) {
    return ResponseEntity
      .status(ErrorCode.USER_NOT_FOUND.getStatus())
      .body(ResponseDto.error(
        ErrorCode.USER_NOT_FOUND.getStatus().value(),
        ErrorCode.USER_NOT_FOUND.getMessage()
      ));
  }

  @ExceptionHandler({DisabledException.class, LockedException.class})
  public ResponseEntity<ResponseDto<?>> handleAccountStatus(AuthenticationException ex) {
    return ResponseEntity
      .status(403)
      .body(ResponseDto.error(
        403,
        "계정 사용이 불가능한 상태입니다."
      ));
  }

  // 공통 CustomException 처리
  @ExceptionHandler(CustomException.class)
  public ResponseEntity<ResponseDto<?>> handleCustomException(CustomException ex) {
    ErrorCode code = ex.getErrorCode();
    return ResponseEntity
      .status(code.getStatus())
      .body(ResponseDto.error(
        code.getStatus().value(),
        code.getMessage()
      ));
  }

  // 예상 못한 모든 예외 처리
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ResponseDto<?>> handleAll(Exception ex) {
    return ResponseEntity
      .status(ErrorCode.INTERNAL_SERVER_ERROR.getStatus())
      .body(ResponseDto.error(
        ErrorCode.INTERNAL_SERVER_ERROR.getStatus().value(),
        ErrorCode.INTERNAL_SERVER_ERROR.getMessage() + ex.getMessage()
      ));
  }
}
