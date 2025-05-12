package inu.unithon.backend.domain.member.controller.docs;

import inu.unithon.backend.domain.member.dto.LoginRequestDto;
import inu.unithon.backend.domain.member.dto.SignupRequestDto;
import inu.unithon.backend.global.response.ResponseDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

// todo : MemberController Swagger 문서화
@Tag(name = "users", description = "회원 API")
public interface MemberControllerSpecification {

  @PostMapping("/signup")
  ResponseEntity<ResponseDto<Void>> signUp(@Valid @RequestBody SignupRequestDto requestDto);

  @PostMapping("/login")
  ResponseEntity<ResponseDto<String>> login(@Valid @RequestBody LoginRequestDto requestDto);
}
