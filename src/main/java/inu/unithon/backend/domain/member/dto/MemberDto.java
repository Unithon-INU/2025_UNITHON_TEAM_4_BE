package inu.unithon.backend.domain.member.dto;

import inu.unithon.backend.domain.member.entity.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;


@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberDto{

  @NotBlank(message = "이름은 필수입니다.")
  private String name;

  private String profileImageUrl;

  @NotBlank(message = "이메일은 필수입니다.")
  @Email(message = "유효한 이메일 형식이 아닙니다.")
  private String email;

  @NotBlank(message = "비밀번호는 필수입니다.")
  @Size(min = 8, max = 20, message = "비밀번호는 8자 이상 20자 이하로 입력해주세요.")
  private String password;

  @NotBlank(message = "전화번호는 필수입니다.")
  @Pattern(regexp = "^\\d{10,11}$", message = "전화번호는 숫자만 10~11자리로 입력해주세요.")
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

