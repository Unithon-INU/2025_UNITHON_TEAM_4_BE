package inu.unithon.backend.domain.comment.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
@Schema(description = "댓글 생성 request")
public class CommentCreateRequest {

  @Schema(
    description = "글 아이디",
    example = "1"
  )
  @NotNull(message = "글 아이디를 입력하세요. ")
  private Long postId;

  @Schema(
    description = "댓글",
    example = "example-comment"
  )
  @NotNull(message = "댓글을 입력하세요. ")
  private String content;
}
