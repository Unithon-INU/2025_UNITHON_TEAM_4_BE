package inu.unithon.backend.domain.post.controller.docs;

import inu.unithon.backend.domain.member.entity.CustomUserDetails;
import inu.unithon.backend.domain.post.dto.request.PostCreateRequest;
import inu.unithon.backend.domain.post.dto.request.PostUpdateRequest;
import inu.unithon.backend.domain.post.dto.response.PostResponse;
import inu.unithon.backend.domain.post.dto.response.PostDetailResponse;
import inu.unithon.backend.global.response.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

// todo : PostController Swagger 문서화
@Tag(name = "posts", description = "Posts API")
public interface PostControllerSpecification {

  // todo : create
  @Operation(
    summary = "게시물 생성",
    description = "게시글을 작성하는 API",
    requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
      description = "게시글 생성 요청",
      required = true,
      content = @Content(
        mediaType = MediaType.MULTIPART_FORM_DATA_VALUE
      )
    )
  )
  @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  ResponseEntity<ResponseDto<Void>> createPost(@AuthenticationPrincipal CustomUserDetails userDetails,
                                               @Valid @RequestPart("data") PostCreateRequest rq,
                                               @RequestPart("images") List<MultipartFile> images);

  // todo : read(one)
  ResponseEntity<ResponseDto<PostDetailResponse>> getPost(@PathVariable Long postId);

  // todo : read(all)
  ResponseEntity<ResponseDto<List<PostResponse>>> getAllPosts();

  // todo : update
  ResponseEntity<ResponseDto<PostDetailResponse>> updatePost(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                             @PathVariable Long postId,
                                                             @Valid @RequestBody PostUpdateRequest rq,
                                                             @RequestPart("images") List<MultipartFile> images);

  // todo : delete
  ResponseEntity<ResponseDto<Long>> deletePost(@AuthenticationPrincipal CustomUserDetails userDetails,
                                               @PathVariable Long postId);

  // todo : read(keyword)
  // todo :
  // todo :



}
