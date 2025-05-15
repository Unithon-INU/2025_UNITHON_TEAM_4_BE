package inu.unithon.backend.domain.member.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
@Schema(description = "다른 유저의 프로필 조회 DTO")
public class ProfileRequestDto {

  @Schema(
    description = "PK 값",
    example = "1"
  )
  @NotNull(message = "id 값 입력은 필수입니다.")
  Long id;
}
