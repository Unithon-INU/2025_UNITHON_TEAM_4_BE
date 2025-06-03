package inu.unithon.backend.domain.auth.service;

import inu.unithon.backend.domain.auth.dto.request.LoginRequestDto;
import inu.unithon.backend.domain.auth.dto.request.SignupRequestDto;
import inu.unithon.backend.domain.member.entity.Member;
import inu.unithon.backend.domain.member.repository.MemberRepository;
import inu.unithon.backend.global.exception.CustomException;
import inu.unithon.backend.domain.member.entity.CustomUserDetails;
import inu.unithon.backend.global.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static inu.unithon.backend.global.exception.ErrorCode.*;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{
  private final MemberRepository memberRepository;
  private final AuthenticationManager authenticationManager;
  private final JwtTokenProvider jwtTokenProvider;
  private final PasswordEncoder passwordEncoder;

  public void signUp(SignupRequestDto requestDto) {

    validateEmail(requestDto.getEmail());
    validateName(requestDto.getName());

    Member member = Member.builder()
      .name(requestDto.getName())
      .profileImageUrl(requestDto.getProfileImageUrl())
      .email(requestDto.getEmail())
      .password(passwordEncoder.encode(requestDto.getPassword()))
      .phone(requestDto.getPhone())
      .role(requestDto.getRole())
      .build();

    // todo : 미완성 로직
    validate(member);
    memberRepository.save(member);
  }

  @Transactional(readOnly = true)
  public String login(LoginRequestDto requestDto) {
    UsernamePasswordAuthenticationToken authenticationToken =
      new UsernamePasswordAuthenticationToken(requestDto.getEmail(), requestDto.getPassword());

    authenticationManager.authenticate(authenticationToken);

    // 인증이 성공하면 JWT 토큰을 생성하여 반환
    CustomUserDetails userDetails = memberRepository.findByEmail(requestDto.getEmail())
      .map(CustomUserDetails::new)
      .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

    return jwtTokenProvider.generateToken(userDetails);
  }

  // todo : 로그아웃 기능 구현
  public void logout(String token) {}

  // todo : 유저 검증 로직 : 추후 추가
  private void validate(Member member) {}

  private void validateEmail(String email){
    if (memberRepository.existsByEmail(email)) throw new CustomException(EMAIL_DUPLICATED);
  }

  private void validateName(String name) {
    if (memberRepository.existsByName(name)) throw new CustomException(NAME_DUPLICATED);
  }
}
