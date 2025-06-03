package inu.unithon.backend.domain.auth.controller.api;

import inu.unithon.backend.domain.auth.controller.docs.AuthControllerSpecification;
import inu.unithon.backend.domain.auth.dto.request.LoginRequestDto;
import inu.unithon.backend.domain.auth.dto.request.SignupRequestDto;
import inu.unithon.backend.domain.auth.service.AuthService;
import inu.unithon.backend.global.response.ResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController implements AuthControllerSpecification {

  private final AuthService authService;

  @PostMapping("/signup")
  public ResponseEntity<ResponseDto<Void>> signUp(@Valid @RequestBody SignupRequestDto requestDto) {
    authService.signUp(requestDto);
    return ResponseEntity
      .status(HttpStatus.CREATED)
      .body(ResponseDto.success("유저 등록 성공"));
  }

  @PostMapping("/login")
  public ResponseEntity<ResponseDto<String>> login(@Valid @RequestBody LoginRequestDto requestDto) {
    String token = authService.login(requestDto);
    return ResponseEntity
      .status(HttpStatus.OK)
      .body(ResponseDto.success("유저 로그인 성공", token));
  }

}
