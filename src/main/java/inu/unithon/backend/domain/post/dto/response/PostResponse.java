package inu.unithon.backend.domain.post.dto.response;

import inu.unithon.backend.domain.post.dto.CommentDto;
import inu.unithon.backend.domain.post.dto.ImageDto;
import inu.unithon.backend.domain.post.dto.WriterDto;
import inu.unithon.backend.domain.post.entity.Post;
import lombok.*;

import java.util.List;

// post details
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class PostResponse {

  private Long postId;
  private Long likes;
  private String title;
  private String content;
  private String thumbnailUrl;
  private List<ImageDto> images;
  private List<CommentDto> comments;
  private WriterDto writer;

  public static PostResponse from(Post post) {
    return PostResponse.builder()
      .postId(post.getId())
      .likes(post.getLikes())
      .title(post.getTitle())
      .content(post.getContent())
      .thumbnailUrl(post.getThumbnailUrl())
      .images(post.getImages().stream()
        .map(ImageDto::from)
        .toList())
      .comments(post.getComments().stream()
        .map(CommentDto::from)
        .toList())
      .writer(WriterDto.from(post.getMember()))
      .build();
  }
}
