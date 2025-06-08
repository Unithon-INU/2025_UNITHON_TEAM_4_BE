package inu.unithon.backend.domain.auth.controller.docs;

import inu.unithon.backend.domain.auth.dto.request.LoginRequestDto;
import inu.unithon.backend.domain.auth.dto.request.SignupRequestDto;
import inu.unithon.backend.global.response.ResponseDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

// todo : AuthController Swagger 문서화
@Tag(name = "auth", description = "Auth API")
public interface AuthControllerSpecification {

  /**
   * 회원 가입
   * @param requestDto
   * @return
   */
  @PostMapping("/signup")
  ResponseEntity<ResponseDto<Void>> signUp(@Valid @RequestBody SignupRequestDto requestDto);

  /**
   * 로그인
   * @param requestDto
   * @return
   */
  @PostMapping("/login")
  ResponseEntity<ResponseDto<String>> login(@Valid @RequestBody LoginRequestDto requestDto);

  // todo : 로그아웃
}
