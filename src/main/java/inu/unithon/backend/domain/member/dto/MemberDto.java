package inu.unithon.backend.domain.member.dto;

import inu.unithon.backend.domain.member.entity.Role;
import jakarta.persistence.*;
import lombok.*;


@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberDto{

  private String name;
  private String profileImageUrl;
  private String email;
  private String password;
  private String phone;

  @Enumerated(EnumType.STRING)
  private Role role;

  @Builder
  public MemberDto(String name, String profileImageUrl, String email, String password, String phone) {
    this.name = name;
    this.profileImageUrl = profileImageUrl;
    this.email = email;
    this.password = password;
    this.phone = phone;
  }
}

