package inu.unithon.backend.domain.comment.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
@Schema(description = "댓글 수정 request")
public class CommentUpdateRequest {

  @Schema(
    description = "댓글 아이디",
    example = "1"
  )
  @NotNull(message = "댓글 아이디를 입력하세요. ")
  private Long commentId;

  @Schema(
    description = "댓글",
    example = "example-fixed-comment"
  )
  @NotNull(message = "수정 할 내용을 입력해주세요. ")
  private String comment;
}
