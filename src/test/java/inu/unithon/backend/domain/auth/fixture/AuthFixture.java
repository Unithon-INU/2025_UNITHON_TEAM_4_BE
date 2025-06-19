package inu.unithon.backend.domain.auth.fixture;

import inu.unithon.backend.domain.member.entity.Member;
import inu.unithon.backend.domain.member.entity.Role;
import org.springframework.test.util.ReflectionTestUtils;

public class AuthFixture {

  public static Member create(Long id, String email, String name) {
    Member member = Member.builder()
      .email(email)
      .name(name)
      .profileImageUrl("test-url")
      .password("test-password")
      .role(Role.USER)
      .build();

    // 강제 주입
    ReflectionTestUtils.setField(member, "id", id);
    return member;
  }

  public static Member createAdmin(Long id, String email, String name) {
    Member member = Member.builder()
      .email(email)
      .name(name)
      .profileImageUrl("test-url")
      .password("test-password")
      .role(Role.ADMIN)
      .build();

    // 강제 주입
    ReflectionTestUtils.setField(member, "id", id);
    return member;
  }
}
