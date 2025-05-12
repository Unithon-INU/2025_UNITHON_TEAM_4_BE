package inu.unithon.backend.domain.member.controller.api;
import inu.unithon.backend.domain.member.controller.docs.MemberControllerSpecification;
import inu.unithon.backend.domain.member.dto.LoginRequestDto;
import inu.unithon.backend.domain.member.dto.SignupRequestDto;
import inu.unithon.backend.domain.member.service.MemberService;
import inu.unithon.backend.global.response.ResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class MemberController implements MemberControllerSpecification {

  private final MemberService memberService;

  @PostMapping("/signup")
  public ResponseEntity<ResponseDto<Void>> signUp(@Valid @RequestBody SignupRequestDto requestDto) {
    memberService.signUp(requestDto);
    return ResponseEntity
      .status(HttpStatus.CREATED)
      .body(ResponseDto.success("유저 등록 성공", null));
  }

  @PostMapping("/login")
  public ResponseEntity<ResponseDto<String>> login(@Valid @RequestBody LoginRequestDto requestDto) {
    String token = memberService.login(requestDto);
    return ResponseEntity
      .status(HttpStatus.OK)
      .body(ResponseDto.success("유저 로그인 성공", token));
  }
}
