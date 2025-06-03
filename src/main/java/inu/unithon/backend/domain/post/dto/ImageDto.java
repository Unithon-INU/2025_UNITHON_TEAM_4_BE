package inu.unithon.backend.domain.post.dto;

import inu.unithon.backend.domain.post.entity.PostImage;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Schema(description = "이미지 url DTO")
public class ImageDto {

  @Schema(
    description = "이미지 url",
    example = "www.example.io"
  )
  private String imageUrl;

  public static ImageDto from(PostImage image) {
    return ImageDto.builder()
      .imageUrl(image.getImageUrl())
      .build();
  }
}
