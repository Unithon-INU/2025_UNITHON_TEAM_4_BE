package inu.unithon.backend.domain.post.dto;

import inu.unithon.backend.domain.post.entity.Post;
import lombok.*;

import java.time.LocalDateTime;

/** 간단한 post 정보 **/
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostDto {
  private Long postId;
  private String thumbnailUrl;
  private String title;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  @Builder
  public PostDto(Long postId, String thumbnailUrl, String title, LocalDateTime createdAt, LocalDateTime updatedAt) {
    this.postId = postId;
    this.thumbnailUrl = thumbnailUrl;
    this.title = title;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  public static PostDto fromPost(Post post) {
    return PostDto.builder()
      .postId(post.getId())
      .thumbnailUrl(post.getThumbnailUrl())
      .title(post.getTitle())
      .createdAt(post.getCreatedAt())
      .updatedAt(post.getUpdatedAt())
      .build();
  }
}
