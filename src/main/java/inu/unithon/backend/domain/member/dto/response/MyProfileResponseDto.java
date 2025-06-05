package inu.unithon.backend.domain.member.dto.response;

import inu.unithon.backend.domain.post.dto.PostDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MyProfileResponseDto {

  private String name;
  private String profileImageUrl;
  private String email;

  // 가입 날짜
  private LocalDateTime createdAt;

  // 본인 게시물 갯수
  private Long postCount;

  // 본인 게시물들 조회.
  private List<PostDto> posts;

  @Builder
  public MyProfileResponseDto(String name, String profileImageUrl, String email, LocalDateTime createdAt, Long postCount, List<PostDto> posts) {
    this.name = name;
    this.profileImageUrl = profileImageUrl;
    this.email = email;
    this.createdAt = createdAt;
    this.postCount = postCount;
    this.posts = posts;
  }
}
