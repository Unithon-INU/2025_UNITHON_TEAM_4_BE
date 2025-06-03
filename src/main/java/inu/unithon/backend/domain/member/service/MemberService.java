package inu.unithon.backend.domain.member.service;

import inu.unithon.backend.domain.member.dto.request.UpdatePasswordRequestDto;
import inu.unithon.backend.domain.member.dto.request.UpdateProfileRequestDto;
import inu.unithon.backend.domain.member.dto.response.MyProfileResponseDto;
import inu.unithon.backend.domain.member.dto.response.ProfileResponseDto;
import inu.unithon.backend.domain.member.entity.Member;

public interface MemberService {

  /**
   * 자신의 프로필 조회
   * @param id
   * @return MyProfileResponseDto
   */
  MyProfileResponseDto getMyProfile(Long id);

  /**
   * 남의 프로필 조회
   * @param myId
   * @param id
   * @return ProfileResponseDto
   */
  ProfileResponseDto getProfile(Long myId, Long id);

  /**
   * 프로필 업데이트
   * @param id
   * @param profileRequestDto
   * @return MyProfileResponseDto
   */
  MyProfileResponseDto updateProfile(Long id, UpdateProfileRequestDto profileRequestDto);

  /**
   * 회원 탈퇴
   * @param id
   */
  void deleteProfile(Long id);

  /**
   * 비밀번호 변경
   * @param id
   * @param requestDto
   */
  void updatePassword(Long id, UpdatePasswordRequestDto requestDto);

  Member getMember(Long id);
}
