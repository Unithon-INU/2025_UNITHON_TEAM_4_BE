package inu.unithon.backend.domain.member.dto.response;

import inu.unithon.backend.domain.post.dto.PostDto;
import inu.unithon.backend.domain.post.entity.Post;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProfileResponseDto {

  private String name;
  private String profileImageUrl;
  private List<PostDto> posts;

  @Builder
  public ProfileResponseDto(String name, String profileImageUrl, List<PostDto> posts) {
    this.name = name;
    this.profileImageUrl = profileImageUrl;
    this.posts = posts;
  }
}
