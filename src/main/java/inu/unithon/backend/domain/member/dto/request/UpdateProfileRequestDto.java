package inu.unithon.backend.domain.member.dto.request;

import inu.unithon.backend.domain.member.entity.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
@Schema(description = "프로필 업데이트 DTO")
public class UpdateProfileRequestDto {
  @Schema(
    description = "이름",
    example = "홍길동"
  )
  @NotBlank(message = "이름은 필수 입력 값입니다.")
  private String name;

  private String profileImageUrl;

  @Schema(
    description = "이메일",
    example = "qwer@naver.com"
  )
  @NotBlank(message = "이메일은 필수 입력 값입니다.")
  @Email(message = "유효한 이메일 형식이 아닙니다.")
  private String email;

  @Schema(
    description = "전화 번호",
    example = "01012340783"
  )
  @NotBlank(message = "전화번호는 필수입니다.")
  @Pattern(regexp = "^\\d{10,11}$", message = "전화번호는 숫자만 10~11자리로 입력해주세요.")
  private String phone;

  @Schema(
    description = "사용자/어드민",
    example = "USER/ADMIN"
  )
  @Enumerated(EnumType.STRING)
  private Role role;
}
