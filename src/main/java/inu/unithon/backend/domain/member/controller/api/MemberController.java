package inu.unithon.backend.domain.member.controller.api;

import inu.unithon.backend.domain.member.controller.docs.MemberControllerSpecification;
import inu.unithon.backend.domain.member.dto.request.UpdatePasswordRequestDto;
import inu.unithon.backend.domain.member.dto.request.UpdateProfileRequestDto;
import inu.unithon.backend.domain.member.dto.response.MyProfileResponseDto;
import inu.unithon.backend.domain.member.dto.response.UpdateProfileResponseDto;
import inu.unithon.backend.domain.member.dto.request.ProfileRequestDto;
import inu.unithon.backend.domain.member.dto.response.ProfileResponseDto;
import inu.unithon.backend.domain.member.entity.CustomUserDetails;
import inu.unithon.backend.domain.member.entity.Member;
import inu.unithon.backend.domain.member.service.MemberService;
import inu.unithon.backend.global.response.ResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class MemberController implements MemberControllerSpecification {

  private final MemberService memberService;

  @Override
  @GetMapping
  public ResponseEntity<ResponseDto<MyProfileResponseDto>> myProfile(@AuthenticationPrincipal CustomUserDetails userDetails) {

    return ResponseEntity
      .status(HttpStatus.OK)
      .body(ResponseDto.success("프로필 조회 성공", memberService.getMyProfile(userDetails.getMember().getId())));
  }

  @Override
  @PostMapping
  public ResponseEntity<ResponseDto<ProfileResponseDto>> profile(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                                 @Valid @RequestBody ProfileRequestDto requestDto) {
    return ResponseEntity
      .status(HttpStatus.OK)
      .body(ResponseDto.success("프로필 조회 성공", memberService.getProfile(userDetails.getMember().getId(), requestDto.getId())));
  }

  @Override
  @PatchMapping
  public ResponseEntity<ResponseDto<UpdateProfileResponseDto>> updateProfile(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                                             @Valid @RequestBody UpdateProfileRequestDto requestDto) {
    return ResponseEntity
      .status(HttpStatus.OK)
      .body(ResponseDto.success("프로필 업데이트 성공", memberService.updateProfile(userDetails.getMember().getId(), requestDto)));
  }

  @Override
  @DeleteMapping
  public ResponseEntity<ResponseDto<Void>> deleteProfile(@AuthenticationPrincipal CustomUserDetails userDetails) {
    memberService.deleteProfile(userDetails.getMember().getId());
    return ResponseEntity
      .status(HttpStatus.OK)
      .body(ResponseDto.success("유저 삭제 성공"));

  }

  @Override
  @PatchMapping("/password")
  public ResponseEntity<ResponseDto<Void>> updatePassword(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                          @Valid @RequestBody UpdatePasswordRequestDto requestDto){
    memberService.updatePassword(userDetails.getMember().getId(), requestDto);
    return ResponseEntity
      .status(HttpStatus.OK)
      .body(ResponseDto.success("비밀번호 변경 완료"));
  }

  @Override
  @PostMapping(value = "profile-image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<ResponseDto<Void>> updateProfileImage(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                              @RequestPart MultipartFile image) {
    memberService.updateProfileImage(userDetails.getMember().getId(), image);

    return ResponseEntity
      .status(HttpStatus.OK)
      .body(ResponseDto.success("프로필 이미지 변경 완료"));
  }

  @Override
  @GetMapping("/admin/get")
  public ResponseEntity<ResponseDto<Page<Member>>> getAllUsers(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                               @RequestParam(defaultValue = "0") int page,
                                                               @RequestParam(defaultValue = "10") int size) {

    return ResponseEntity
      .status(HttpStatus.OK)
      .body(ResponseDto.success("관리자 - 유저 조회 성공", memberService.getMembers(userDetails.getId() ,page, size)));

  }

  @Override
  @GetMapping("/admin/delete")
  public ResponseEntity<ResponseDto<Long>> deleteUser(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                      @RequestParam Long targetId) {

    return ResponseEntity
      .status(HttpStatus.OK)
      .body(ResponseDto.success("관리자 - 유저 삭제 성공", memberService.deleteMember(userDetails.getId(), targetId)));
  }
}
