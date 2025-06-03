package inu.unithon.backend.domain.post.controller.api;

import inu.unithon.backend.domain.member.entity.CustomUserDetails;
import inu.unithon.backend.domain.post.controller.docs.PostControllerSpecification;
import inu.unithon.backend.domain.post.dto.request.PostCreateRequest;
import inu.unithon.backend.domain.post.dto.request.PostUpdateRequest;
import inu.unithon.backend.domain.post.dto.response.PostResponse;
import inu.unithon.backend.domain.post.service.PostService;
import inu.unithon.backend.global.response.ResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/posts")
public class PostController implements PostControllerSpecification {

  private final PostService postService;

  @Override
  @PostMapping
  public ResponseEntity<ResponseDto<Void>> createPost(@AuthenticationPrincipal CustomUserDetails userDetails, @Valid @RequestBody PostCreateRequest rq) {

    postService.create(userDetails.getMember().getId(), rq);
    return ResponseEntity
      .status(HttpStatus.OK)
      .body(ResponseDto.success("게시물 생성 완료"));
  }

  @Override
  @GetMapping("/{postId}")
  public ResponseEntity<ResponseDto<PostResponse>> getPost(@PathVariable Long postId) {

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
  @PatchMapping("/{postId}")
  public ResponseEntity<ResponseDto<PostResponse>> updatePost(@AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable Long postId, @Valid @RequestBody PostUpdateRequest rq) {

    return ResponseEntity
      .status(HttpStatus.OK)
      .body(ResponseDto.success("게시물 수정 완료", postService.updatePost(userDetails.getMember().getId(), postId,rq)));
  }

  @Override
  @DeleteMapping("/{postId}")
  public ResponseEntity<ResponseDto<Long>> deletePost(@AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable Long postId) {

    postService.delete(userDetails.getMember().getId(), postId);
    return ResponseEntity
      .status(HttpStatus.OK)
      .body(ResponseDto.success("게시물 삭제 완료", postId));
  }
}
