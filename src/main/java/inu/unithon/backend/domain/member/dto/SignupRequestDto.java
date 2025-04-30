package inu.unithon.backend.domain.member.dto;

import inu.unithon.backend.domain.member.entity.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequestDto {

  @NotBlank(message = "이름은 필수 입력 값입니다.")
  private String name;

  private String profileImageUrl;

  @NotBlank(message = "이메일은 필수 입력 값입니다.")
  private String email;

  @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
  // @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,20}$")
  private String password;


  private String phone;
  private Role role;
}
