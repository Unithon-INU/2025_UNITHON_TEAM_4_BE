package inu.unithon.backend.domain.post.dto.response;

import inu.unithon.backend.domain.post.dto.WriterDto;
import inu.unithon.backend.domain.post.entity.Post;
import lombok.*;

import java.time.LocalDateTime;

// post details
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class PostResponse {

  private Long postId;
  private Long likes;
  private String title;
  private String thumbnailUrl;
  private WriterDto writer;

  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  public static PostResponse fromPost(Post post) {
    return PostResponse.builder()
      .postId(post.getId())
      .likes(post.getLikes())
      .title(post.getTitle())
      .thumbnailUrl(post.getThumbnailUrl())
      .writer(WriterDto.fromMember(post.getMember()))
      .createdAt(post.getCreatedAt())
      .updatedAt(post.getUpdatedAt())
      .build();
  }
}
