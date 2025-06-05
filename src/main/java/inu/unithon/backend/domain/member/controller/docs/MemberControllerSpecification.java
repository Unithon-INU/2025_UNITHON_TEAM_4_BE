package inu.unithon.backend.domain.member.controller.docs;

import inu.unithon.backend.domain.member.dto.request.UpdatePasswordRequestDto;
import inu.unithon.backend.domain.member.dto.request.UpdateProfileRequestDto;
import inu.unithon.backend.domain.member.dto.response.MyProfileResponseDto;
import inu.unithon.backend.domain.member.dto.response.UpdateProfileResponseDto;
import inu.unithon.backend.domain.member.dto.request.ProfileRequestDto;
import inu.unithon.backend.domain.member.dto.response.ProfileResponseDto;
import inu.unithon.backend.domain.member.entity.CustomUserDetails;
import inu.unithon.backend.global.response.ResponseDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

// todo : AuthController Swagger 문서화
@Tag(name = "users", description = "Users API")
public interface MemberControllerSpecification {

  // todo : 프로필 조회(my)
  ResponseEntity<ResponseDto<MyProfileResponseDto>> myProfile(@AuthenticationPrincipal CustomUserDetails userDetails);

  // todo : 프로필 조회(other)
  ResponseEntity<ResponseDto<ProfileResponseDto>> profile(@AuthenticationPrincipal CustomUserDetails userDetails, @Valid @RequestBody ProfileRequestDto requestDto);

  // todo : 프로필 수정
  ResponseEntity<ResponseDto<UpdateProfileResponseDto>> updateProfile(@AuthenticationPrincipal CustomUserDetails userDetails, @Valid @RequestBody UpdateProfileRequestDto requestDto);

  // todo : 회원 탈퇴
  ResponseEntity<ResponseDto<Void>> deleteProfile(@AuthenticationPrincipal CustomUserDetails userDetails);

  // todo : 비밀번호 수정
  ResponseEntity<ResponseDto<Void>> updatePassword(@AuthenticationPrincipal CustomUserDetails userDetails, @Valid @RequestBody UpdatePasswordRequestDto requestDto);

  // todo : 프로필 사진만 수정
  ResponseEntity<ResponseDto<Void>> updateProfileImage(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                       @RequestPart MultipartFile image);
}
