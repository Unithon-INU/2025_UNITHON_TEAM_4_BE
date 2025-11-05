package inu.unithon.backend.domain.member.service;

import inu.unithon.backend.domain.member.dto.request.UpdatePasswordRequestDto;
import inu.unithon.backend.domain.member.dto.request.UpdateProfileRequestDto;
import inu.unithon.backend.domain.member.dto.response.MyProfileResponseDto;
import inu.unithon.backend.domain.member.dto.response.UpdateProfileResponseDto;
import inu.unithon.backend.domain.member.dto.response.ProfileResponseDto;
import inu.unithon.backend.domain.member.entity.Member;
import inu.unithon.backend.domain.member.entity.Role;
import inu.unithon.backend.domain.member.repository.MemberRepository;
import inu.unithon.backend.domain.post.dto.PostDto;
import inu.unithon.backend.global.exception.CustomException;
import inu.unithon.backend.global.exception.CommonErrorCode;
import inu.unithon.backend.global.service.S3Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

import static inu.unithon.backend.global.exception.CommonErrorCode.*;
import static inu.unithon.backend.global.exception.CommonErrorCode.INVALID_PASSWORD;
import static inu.unithon.backend.global.exception.CommonErrorCode.SAME_PASSWORD;
import static inu.unithon.backend.global.exception.UserErrorCode.*;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class MemberServiceImpl implements MemberService{

  @Value("${default.profile.image}")
  private String defaultProfileImageUrl;

  private final MemberRepository memberRepository;
  private final PasswordEncoder passwordEncoder;
  private final S3Service s3Service;

  // todo : 페이지네이션 적용
  @Override
  @Transactional(readOnly = true)
  public MyProfileResponseDto getMyProfile(Long id) {
    Member member = getMember(id);

    log.info("getMyProfile : {}", id);

    return MyProfileResponseDto.builder()
      .name(member.getName())
      .profileImageUrl(member.getProfileImageUrl())
      .email(member.getEmail())
      .createdAt(member.getCreatedAt())
      .postCount(member.getPostCount())
      .posts(member.getPosts().stream()
        .map(PostDto::fromPost)
        .toList())
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
      .posts(member.getPosts().stream()
        .map(PostDto::fromPost)
        .toList())
      .build();
  }

  // FIXME : 프로필 수정 -> 마지막에 로그아웃 시켜야됨
  @Override
  public UpdateProfileResponseDto updateProfile(Long id, UpdateProfileRequestDto profileRequestDto) {

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

    return UpdateProfileResponseDto.builder()
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
  public void updateProfileImage(Long id, MultipartFile file) {
    Member member = getMember(id);

    if (!Objects.equals(member.getProfileImageUrl(), defaultProfileImageUrl)) {
      s3Service.deleteImage(member.getProfileImageUrl());
    }

    member.updateProfileImage(s3Service.uploadImage(file));
  }

  @Override
  @Transactional(readOnly = true)
  public Page<Member> getMembers(Long id, int page, int size) {
    // id가 ADMIN인지 체크
    Member member = getMember(id);
    if(member.getRole()!= Role.ADMIN) throw new CustomException(CommonErrorCode.FORBIDDEN);

    Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");

    log.info("getMembers, Admin Id : {}", id);
    return memberRepository.findAll(pageable);
  }

  @Override
  public Long deleteMember(Long myId, Long targetId) {
    Member member = getMember(myId);
    if(member.getRole()!= Role.ADMIN) throw new CustomException(CommonErrorCode.FORBIDDEN);

    Member targetMember = getMember(targetId);
    memberRepository.delete(targetMember);
    log.info("deleteMember : {}", targetId);

    return targetId;
  }

  @Override
  public Member getMember(Long id) {
    return memberRepository.findById(id)
      .orElseThrow(() -> new CustomException(USER_NOT_FOUND));
  }

  private void validateEmail(String email){
    if (memberRepository.existsByEmail(email)) throw new CustomException(EMAIL_DUPLICATED);
  }

  private void validateName(String name) {
    if (memberRepository.existsByName(name)) throw new CustomException(NAME_DUPLICATED);
  }
}
