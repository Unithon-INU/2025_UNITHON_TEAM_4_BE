package inu.unithon.backend.domain.post.controller.api;

import inu.unithon.backend.domain.member.entity.CustomUserDetails;
import inu.unithon.backend.domain.post.controller.docs.PostControllerSpecification;
import inu.unithon.backend.domain.post.dto.request.PostCreateRequest;
import inu.unithon.backend.domain.post.dto.request.PostUpdateRequest;
import inu.unithon.backend.domain.post.dto.response.PostResponse;
import inu.unithon.backend.domain.post.dto.response.PostDetailResponse;
import inu.unithon.backend.domain.post.service.PostService;
import inu.unithon.backend.global.response.ResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/posts")
public class PostController implements PostControllerSpecification {

  private final PostService postService;

  @Override
  @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<ResponseDto<Void>> createPost(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                      @Valid @RequestPart("data") PostCreateRequest rq,
                                                      @RequestPart("images") List<MultipartFile> images) {

    postService.create(userDetails.getMember().getId(), rq, images);
    return ResponseEntity
      .status(HttpStatus.OK)
      .body(ResponseDto.success("게시물 생성 완료"));
  }

  @Override
  @GetMapping("/{postId}")
  public ResponseEntity<ResponseDto<PostDetailResponse>> getPost(@PathVariable Long postId) {

    return ResponseEntity
      .status(HttpStatus.OK)
      .body(ResponseDto.success("게시물 조회 완료", postService.getPost(postId)));
  }

  @Override
  @GetMapping
  public ResponseEntity<ResponseDto<List<PostResponse>>> getAllPosts() {

    return ResponseEntity
      .status(HttpStatus.OK)
      .body(ResponseDto.success("전체 게시물 조회 완료", postService.getAllPosts()));
  }

  @Override
  @PatchMapping(value = "/{postId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<ResponseDto<PostDetailResponse>> updatePost(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                                    @PathVariable Long postId,
                                                                    @Valid @RequestPart("data") PostUpdateRequest rq,
                                                                    @RequestPart("images") List<MultipartFile> images) {

    return ResponseEntity
      .status(HttpStatus.OK)
      .body(ResponseDto.success("게시물 수정 완료", postService.updatePost(userDetails.getMember().getId(), postId, rq, images)));
  }

  @Override
  @DeleteMapping("/{postId}")
  public ResponseEntity<ResponseDto<Long>> deletePost(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                      @PathVariable Long postId) {

    postService.delete(userDetails.getMember().getId(), postId);
    return ResponseEntity
      .status(HttpStatus.OK)
      .body(ResponseDto.success("게시물 삭제 완료", postId));
  }
}
