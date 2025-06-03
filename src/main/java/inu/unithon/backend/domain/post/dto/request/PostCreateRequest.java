package inu.unithon.backend.domain.post.dto.request;

import inu.unithon.backend.domain.post.dto.ImageDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@Schema(description = "게시물 생성 DTO")
public class PostCreateRequest {

  @Schema(
    description = "제목",
    example = "example-title"
  )
  @NotNull(message = "제목을 입력해주세요.")
  private String title;

  @Schema(
    description = "내용",
    example = "example-content"
  )
  private String content;

  @Schema(
    description = "썸네일 Url",
    example = "www.thumbnailurl.com"
  )
  @NotNull(message = "사진을 넣어주세요.")
  private String thumbnailUrl;

  @Schema(
    description = "이미지 Url",
    example = "www.wwww.io"
  )
  private List<ImageDto> images;
}
