package inu.unithon.backend.domain.member.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
@Schema(description = "비밀번호 변경 DTO")
public class UpdatePasswordRequestDto {

  @Schema(
    description = "비밀번호",
    example = "qwerqwer1@"
  )
  @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
  // @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,20}$")
  @Size(min = 8, max = 20, message = "비밀번호는 8자 이상 20자 이하로 입력해주세요.")
  private String oldPassword;

  @Schema(
    description = "비밀번호",
    example = "qwerqwer12@"
  )
  @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
  // @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,20}$")
  @Size(min = 8, max = 20, message = "비밀번호는 8자 이상 20자 이하로 입력해주세요.")
  private String newPassword;

}
