package inu.unithon.backend.domain.member.service;

import inu.unithon.backend.domain.member.dto.request.UpdatePasswordRequestDto;
import inu.unithon.backend.domain.member.dto.request.UpdateProfileRequestDto;
import inu.unithon.backend.domain.member.dto.response.MyProfileResponseDto;
import inu.unithon.backend.domain.member.dto.response.ProfileResponseDto;
import inu.unithon.backend.domain.member.entity.Member;
import inu.unithon.backend.domain.member.repository.MemberRepository;
import inu.unithon.backend.global.exception.CustomException;
import inu.unithon.backend.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

import static inu.unithon.backend.global.exception.ErrorCode.*;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class MemberServiceImpl implements MemberService{

  private final MemberRepository memberRepository;
  private final PasswordEncoder passwordEncoder;

  @Override
  @Transactional(readOnly = true)
  public MyProfileResponseDto getMyProfile(Long id) {
    Member member = getMember(id);

    log.info("getMyProfile : {}", id);
    return MyProfileResponseDto.builder()
      .name(member.getName())
      .profileImageUrl(member.getProfileImageUrl())
      .email(member.getEmail())
      .build();
  }

  @Override
  @Transactional(readOnly = true)
  public ProfileResponseDto getProfile(Long myId, Long id) {
    Member member = getMember(id);

    log.info("{} -> getProfile : {}", myId, id);
    return ProfileResponseDto.builder()
      .name(member.getName())
      .profileImageUrl(member.getProfileImageUrl())
      // todo : 페이지네이션 적용 공부
      .posts(member.getPosts())
      .build();
  }

  // FIXME : 프로필 수정 -> 마지막에 로그아웃 시켜야됨
  @Override
  public MyProfileResponseDto updateProfile(Long id, UpdateProfileRequestDto profileRequestDto) {

    Member member = getMember(id);
    if(!Objects.equals(member.getEmail(), profileRequestDto.getEmail())) {
      validateEmail(profileRequestDto.getEmail());
    }
    if(!Objects.equals(member.getName(), profileRequestDto.getName())) {
      validateName(profileRequestDto.getName());
    }

    member.updateMember(
      profileRequestDto.getName(),
      profileRequestDto.getEmail(),
      profileRequestDto.getProfileImageUrl(),
      profileRequestDto.getRole());
    log.info("updateProfile : {}", id);

    return MyProfileResponseDto.builder()
      .name(member.getName())
      .profileImageUrl(member.getProfileImageUrl())
      .email(member.getEmail())
      .build();
  }

  @Override
  public void deleteProfile(Long id) {

    log.info("deleteProfile : {}", id);
    memberRepository.deleteById(id);
  }

  // FIXME : 비밀번호 수정 -> 마지막에 로그아웃 시켜야됨
  @Override
  public void updatePassword(Long id, UpdatePasswordRequestDto requestDto) {

    String oldPassword = requestDto.getOldPassword();
    String newPassword = requestDto.getNewPassword();
    Member member = getMember(id);
    if (!passwordEncoder.matches(oldPassword, member.getPassword())) {
      throw new CustomException(INVALID_PASSWORD);
    }
    if (passwordEncoder.matches(newPassword, member.getPassword())) {
      throw new CustomException(SAME_PASSWORD);
    }

    log.info("updatePassword : {}", id);
    member.updatePassword(passwordEncoder.encode(newPassword));
  }

  @Override
  public Member getMember(Long id) {
    return memberRepository.findById(id)
      .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
  }

  private void validateEmail(String email){
    if (memberRepository.existsByEmail(email)) throw new CustomException(EMAIL_DUPLICATED);
  }

  private void validateName(String name) {
    if (memberRepository.existsByName(name)) throw new CustomException(NAME_DUPLICATED);
  }
}
