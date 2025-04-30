package inu.unithon.backend.global.security;

import inu.unithon.backend.domain.member.entity.Member;
import inu.unithon.backend.domain.member.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Getter
@AllArgsConstructor
public class CustomUserDetails implements UserDetails {

  private final Member member;

  // 권한
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + member.getRole().name()));
  }

  @Override
  public String getPassword() {
    return member.getPassword(); // DB에 저장된 암호화된 비밀번호
  }

  @Override
  public String getUsername() {
    return member.getEmail(); // 로그인 ID
  }

  public Role getRole() {
    return member.getRole();
  }

  // 계정 상태 관련 설정 (기본 true면 OK)
  @Override public boolean isAccountNonExpired() {
    return true;
  }
  @Override public boolean isAccountNonLocked() {
    return true;
  }
  @Override public boolean isCredentialsNonExpired() {
    return true;
  }
  @Override public boolean isEnabled() {
    return true;
  }

}

