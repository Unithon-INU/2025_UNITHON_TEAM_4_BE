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
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

// todo : PostController Swagger 문서화
@Tag(name = "posts", description = "Posts API")
public interface PostControllerSpecification {

  /**
   * 게시물 생성
   * @param userDetails
   * @param rq
   * @param images
   * @return
   */
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
  ResponseEntity<ResponseDto<Void>> createPost(@AuthenticationPrincipal CustomUserDetails userDetails,
                                               @Valid @RequestPart("data") PostCreateRequest rq,
                                               @RequestPart("images") List<MultipartFile> images);

  /**
   * 단일 게시물 조회
   * @param postId
   * @return
   */
  ResponseEntity<ResponseDto<PostDetailResponse>> getPost(@PathVariable Long postId);

  /**
   * 전체 게시물 조회
   * @param page
   * @return
   */
  ResponseEntity<ResponseDto<Page<PostResponse>>> getAllPosts(@RequestParam(defaultValue = "0") int page);

  /**
   * 게시물 수정
   * @param userDetails
   * @param postId
   * @param rq
   * @param images
   * @return
   */
  ResponseEntity<ResponseDto<PostDetailResponse>> updatePost(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                             @PathVariable Long postId,
                                                             @Valid @RequestBody PostUpdateRequest rq,
                                                             @RequestPart("images") List<MultipartFile> images);

  /**
   * 게시물 삭제
   * @param userDetails
   * @param postId
   * @return
   */
  ResponseEntity<ResponseDto<Long>> deletePost(@AuthenticationPrincipal CustomUserDetails userDetails,
                                               @PathVariable Long postId);

  // todo : read(keyword)
  // todo :
  // todo :



}
