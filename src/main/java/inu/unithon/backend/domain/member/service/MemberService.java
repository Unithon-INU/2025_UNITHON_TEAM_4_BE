package inu.unithon.backend.domain.member.service;

import inu.unithon.backend.domain.member.dto.LoginRequestDto;
import inu.unithon.backend.domain.member.dto.SignupRequestDto;
import inu.unithon.backend.domain.member.entity.Member;
import inu.unithon.backend.domain.member.repository.MemberRepository;
import inu.unithon.backend.global.exception.EmailAlreadyExistsException;
import inu.unithon.backend.global.security.CustomUserDetails;
import inu.unithon.backend.global.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly=true)
@RequiredArgsConstructor
public class MemberService {
  private final MemberRepository memberRepository;
  private final AuthenticationManager authenticationManager;
  private final JwtTokenProvider jwtTokenProvider;
  private final PasswordEncoder passwordEncoder;

  @Transactional
  public void signUp(SignupRequestDto requestDto) {

    if (memberRepository.existsByEmail(requestDto.getEmail())) throw new EmailAlreadyExistsException("이메일이 이미 존재합니다");

    Member member = Member.builder()
      .name(requestDto.getName())
      .profileImageUrl(requestDto.getProfileImageUrl())
      .email(requestDto.getEmail())
      .password(passwordEncoder.encode(requestDto.getPassword()))
      .phone(requestDto.getPhone())
      .role(requestDto.getRole())
      .build();

    // 미완성 로직
    validate(member);
    memberRepository.save(member);
  }

  public String login(LoginRequestDto requestDto) {
    UsernamePasswordAuthenticationToken authenticationToken =
      new UsernamePasswordAuthenticationToken(requestDto.getEmail(), requestDto.getPassword());

    authenticationManager.authenticate(authenticationToken);

    // 인증이 성공하면 JWT 토큰을 생성하여 반환
    CustomUserDetails userDetails = memberRepository.findByEmail(requestDto.getEmail())
      .map(CustomUserDetails::new)
      .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다"));

    return jwtTokenProvider.generateToken(userDetails);
  }

  private void validate(Member member){
    // 유저 검증 로직
    // 추후 추가
  }
}
