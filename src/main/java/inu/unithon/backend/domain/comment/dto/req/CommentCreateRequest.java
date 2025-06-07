package inu.unithon.backend.domain.comment.dto.req;

import lombok.Getter;

@Getter
public class CommentCreateRequest {

  private Long postId;
  private String content;
}
