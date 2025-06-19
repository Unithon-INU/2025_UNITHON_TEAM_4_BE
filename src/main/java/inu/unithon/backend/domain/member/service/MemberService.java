package inu.unithon.backend.domain.member.service;

import inu.unithon.backend.domain.member.dto.request.UpdatePasswordRequestDto;
import inu.unithon.backend.domain.member.dto.request.UpdateProfileRequestDto;
import inu.unithon.backend.domain.member.dto.response.MyProfileResponseDto;
import inu.unithon.backend.domain.member.dto.response.UpdateProfileResponseDto;
import inu.unithon.backend.domain.member.dto.response.ProfileResponseDto;
import inu.unithon.backend.domain.member.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

public interface MemberService {
  /** ROLE_USER */

  /**
   * 자신의 프로필 조회
   * @param id
   * @return UpdateProfileResponseDto
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
   * @return UpdateProfileResponseDto
   */
  UpdateProfileResponseDto updateProfile(Long id, UpdateProfileRequestDto profileRequestDto);

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

  /**
   * 프로필 사진 업데이트
   */
  void updateProfileImage(Long id, MultipartFile file);

  /** ROLE_ADMIN */

  /**
   * ADMIN_모든 유저 조회
   * @param id
   * @param page
   * @param size
   * @return
   */
  Page<Member> getMembers(Long id, int page, int size);

  /**
   * ADMIN_유저 삭제
   * @param myId
   * @param targetId
   * @return targetId
   */
  Long deleteMember(Long myId, Long targetId);

  /** Common */
  Member getMember(Long id);
}
