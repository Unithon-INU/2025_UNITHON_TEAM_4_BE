package inu.unithon.backend.domain.post.controller.docs;

import inu.unithon.backend.domain.member.entity.CustomUserDetails;
import inu.unithon.backend.domain.post.dto.request.PostCreateRequest;
import inu.unithon.backend.domain.post.dto.request.PostUpdateRequest;
import inu.unithon.backend.domain.post.dto.response.PostResponse;
import inu.unithon.backend.global.response.ResponseDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

// todo : PostController Swagger 문서화
@Tag(name = "posts", description = "Posts API")
public interface PostControllerSpecification {

  // todo : create
  ResponseEntity<ResponseDto<Void>> createPost(@AuthenticationPrincipal CustomUserDetails userDetails, @Valid @RequestBody PostCreateRequest rq);

  // todo : read(one)
  ResponseEntity<ResponseDto<PostResponse>> getPost(@PathVariable Long postId);

  // todo : read(all)
  ResponseEntity<ResponseDto<List<PostResponse>>> getAllPosts();

  // todo : update
  ResponseEntity<ResponseDto<PostResponse>> updatePost(@AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable Long postId, @Valid @RequestBody PostUpdateRequest rq);

  // todo : delete
  ResponseEntity<ResponseDto<Long>> deletePost(@AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable Long postId);

  // todo : read(keyword)
  // todo :
  // todo :



}
