package inu.unithon.backend.domain.post.dto.response;

import inu.unithon.backend.domain.comment.dto.CommentDto;
import inu.unithon.backend.domain.post.dto.ImageDto;
import inu.unithon.backend.domain.post.dto.WriterDto;
import inu.unithon.backend.domain.post.entity.Post;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

// post details
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class PostDetailResponse {

  private Long postId;
  private Long likes;
  private String title;
  private String content;
  private String thumbnailUrl;
  private List<ImageDto> images;
  private List<CommentDto> comments;
  private WriterDto writer;

  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  public static PostDetailResponse fromPost(Post post) {
    return PostDetailResponse.builder()
      .postId(post.getId())
      .likes(post.getLikes())
      .title(post.getTitle())
      .content(post.getContent())
      .thumbnailUrl(post.getThumbnailUrl())
      .images(post.getImages().stream()
        .map(ImageDto::from)
        .toList())
      .comments(post.getComments().stream()
        .map(CommentDto::fromComment)
        .toList())
      .writer(WriterDto.fromMember(post.getMember()))
      .createdAt(post.getCreatedAt())
      .updatedAt(post.getUpdatedAt())
      .build();
  }
}
