package inu.unithon.backend.domain.auth.service;

import inu.unithon.backend.domain.auth.dto.request.LoginRequestDto;
import inu.unithon.backend.domain.auth.dto.request.SignupRequestDto;

public interface AuthService {

  /**
   * 회원가입
   * @param requestDto
   */
  void signUp(SignupRequestDto requestDto);

  /**
   * 로그인
   * @param requestDto
   * @return token
   */
  String login(LoginRequestDto requestDto);

  /**
   * 로그아웃
   * @param token
   */
  void logout(String token);

}
