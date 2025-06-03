package inu.unithon.backend.domain.auth.dto.request;

import inu.unithon.backend.domain.member.entity.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
@Schema(description = "회원 가입 DTO")
public class SignupRequestDto {

  @Schema(
    description = "이름",
    example = "홍길동"
  )
  @NotBlank(message = "이름은 필수 입력 값입니다.")
  private String name;

  @Schema(
    description = "이메일",
    example = "qwer@naver.com"
  )
  @NotBlank(message = "이메일은 필수 입력 값입니다.")
  @Email(message = "유효한 이메일 형식이 아닙니다.")
  private String email;

  @Schema(
    description = "비밀번호",
    example = "qwerqwer1@"
  )
  @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
  // @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,20}$")
  @Size(min = 8, max = 20, message = "비밀번호는 8자 이상 20자 이하로 입력해주세요.")
  private String password;

  @Schema(
    description = "사용자/어드민",
    example = "USER/ADMIN"
  )
  @Enumerated(EnumType.STRING)
  private Role role;
}
