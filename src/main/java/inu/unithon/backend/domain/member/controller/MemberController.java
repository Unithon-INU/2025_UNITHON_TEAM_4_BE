package inu.unithon.backend.domain.member.controller;
import inu.unithon.backend.domain.member.dto.LoginRequestDto;
import inu.unithon.backend.domain.member.dto.SignupRequestDto;
import inu.unithon.backend.domain.member.service.MemberService;
import inu.unithon.backend.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class MemberController {

  private final MemberService memberService;

  @PostMapping("/signup")
  public ResponseEntity<ApiResponse<Void>> signUp(@RequestBody SignupRequestDto requestDto) {
    memberService.signUp(requestDto);
    return ResponseEntity
      .status(HttpStatus.CREATED)
      .body(ApiResponse.success("유저 등록 성공", null));
  }

  @PostMapping("/login")
  public ResponseEntity<ApiResponse<String>> login(@RequestBody LoginRequestDto requestDto) {
    String token = memberService.login(requestDto);
    return ResponseEntity
      .status(HttpStatus.OK)
      .body(ApiResponse.success("유저 로그인 성공", token));
  }
}
