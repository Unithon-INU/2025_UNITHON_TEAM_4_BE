package inu.unithon.backend.domain.post.dto;

import inu.unithon.backend.domain.comment.entity.Comment;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommentDto {
  private String content;
  private String writerId;
  private String writerName;
  private String writerProfileImageUrl;

  public static CommentDto from(Comment comment) {
    return CommentDto.builder()
      .content(comment.getContent())
      .writerId(comment.getMemberId())
      .writerName(comment.getMemberName())
      .writerProfileImageUrl(comment.getMemberProfileImageUrl())
      .build();
  }
}
