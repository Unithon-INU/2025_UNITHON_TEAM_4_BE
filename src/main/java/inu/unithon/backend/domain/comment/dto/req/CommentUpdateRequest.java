package inu.unithon.backend.domain.comment.dto.req;

import lombok.Getter;

@Getter
public class CommentUpdateRequest {
  private Long commentId;
  private String comment;
}
