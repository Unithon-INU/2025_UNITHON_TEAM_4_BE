package inu.unithon.backend.domain.member.dto.response;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MyProfileResponseDto {

  private String name;
  private String profileImageUrl;
  private String email;

  @Builder
  public MyProfileResponseDto(String name, String profileImageUrl, String email) {
    this.name = name;
    this.profileImageUrl = profileImageUrl;
    this.email = email;
  }
}
