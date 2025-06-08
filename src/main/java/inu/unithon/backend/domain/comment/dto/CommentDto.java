package inu.unithon.backend.domain.comment.dto;

import inu.unithon.backend.domain.comment.entity.Comment;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

// 게시물 안에서 보이는 댓글 dto
@Getter
@Builder
public class CommentDto {
  private Long commentId;
  private String content;
  private Long writerId;
  private String writerName;
  private String writerProfileImageUrl;

  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  public static CommentDto fromComment(Comment comment) {
    return CommentDto.builder()
      .commentId(comment.getId())
      .content(comment.getContent())
      .writerId(comment.getMemberId())
      .writerName(comment.getMemberName())
      .writerProfileImageUrl(comment.getMemberProfileImageUrl())
      .createdAt(comment.getCreatedAt())
      .updatedAt(comment.getUpdatedAt())
      .build();
  }
}
