package inu.unithon.backend.domain.member.controller.api;

import inu.unithon.backend.domain.member.controller.docs.MemberControllerSpecification;
import inu.unithon.backend.domain.member.dto.request.UpdatePasswordRequestDto;
import inu.unithon.backend.domain.member.dto.request.UpdateProfileRequestDto;
import inu.unithon.backend.domain.member.dto.response.MyProfileResponseDto;
import inu.unithon.backend.domain.member.dto.request.ProfileRequestDto;
import inu.unithon.backend.domain.member.dto.response.ProfileResponseDto;
import inu.unithon.backend.domain.member.entity.CustomUserDetails;
import inu.unithon.backend.domain.member.service.MemberService;
import inu.unithon.backend.global.response.ResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class MemberController implements MemberControllerSpecification {

  private final MemberService memberService;

  @GetMapping("/profile")
  public ResponseEntity<ResponseDto<MyProfileResponseDto>> myProfile(@AuthenticationPrincipal CustomUserDetails userDetails) {

    return ResponseEntity
      .status(HttpStatus.OK)
      .body(ResponseDto.success("프로필 조회 성공", memberService.getMyProfile(userDetails.getMember().getId())));
  }

  @PostMapping("/profile")
  public ResponseEntity<ResponseDto<ProfileResponseDto>> profile(@AuthenticationPrincipal CustomUserDetails userDetails, @Valid @RequestBody ProfileRequestDto requestDto) {
    return ResponseEntity
      .status(HttpStatus.OK)
      .body(ResponseDto.success("프로필 조회 성공", memberService.getProfile(userDetails.getMember().getId(), requestDto.getId())));
  }

  @PatchMapping("/profile")
  public ResponseEntity<ResponseDto<MyProfileResponseDto>> updateProfile(@AuthenticationPrincipal CustomUserDetails userDetails, @Valid @RequestBody UpdateProfileRequestDto requestDto) {
    return ResponseEntity
      .status(HttpStatus.OK)
      .body(ResponseDto.success("프로필 업데이트 성공", memberService.updateProfile(userDetails.getMember().getId(), requestDto)));
  }

  @DeleteMapping
  public ResponseEntity<ResponseDto<Void>> deleteProfile(@AuthenticationPrincipal CustomUserDetails userDetails) {
    memberService.deleteProfile(userDetails.getMember().getId());
    return ResponseEntity
      .status(HttpStatus.OK)
      .body(ResponseDto.success("유저 삭제 성공"));

  }

  @PatchMapping("/profile/pw")
  public ResponseEntity<ResponseDto<Void>> updatePassword(@AuthenticationPrincipal CustomUserDetails userDetails, @Valid @RequestBody UpdatePasswordRequestDto requestDto){
    memberService.updatePassword(userDetails.getMember().getId(), requestDto);
    return ResponseEntity
      .status(HttpStatus.OK)
      .body(ResponseDto.success("비밀번호 변경 완료"));
  }

}
