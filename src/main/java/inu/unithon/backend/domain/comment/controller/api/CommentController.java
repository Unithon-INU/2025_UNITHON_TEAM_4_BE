package inu.unithon.backend.domain.comment.controller.api;

import inu.unithon.backend.domain.comment.controller.docs.CommentControllerSpecification;
import inu.unithon.backend.domain.comment.dto.req.CommentCreateRequest;
import inu.unithon.backend.domain.comment.dto.req.CommentUpdateRequest;
import inu.unithon.backend.domain.comment.service.CommentService;
import inu.unithon.backend.domain.member.entity.CustomUserDetails;
import inu.unithon.backend.global.response.ResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts/{postId}/comments")
public class CommentController implements CommentControllerSpecification {

  private final CommentService commentService;

  @Override
  @PostMapping
  public ResponseEntity<ResponseDto<Void>> createComment(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                         @PathVariable Long postId,
                                                         @Valid @RequestBody CommentCreateRequest rq) {

    commentService.addComment(
      userDetails.getId(),
      userDetails.getNickName(),
      userDetails.getProfileUrl(),
      rq);

    return ResponseEntity
      .status(HttpStatus.OK)
      .body(ResponseDto.success("댓글 생성 성공"));
  }

  @Override
  @PatchMapping("/{commentId}")
  public ResponseEntity<ResponseDto<Void>> updateComment(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                         @PathVariable Long postId,
                                                         @PathVariable Long commentId,
                                                         @RequestBody @Valid CommentUpdateRequest rq) {

    commentService.updateComment(
      userDetails.getId(),
      userDetails.getNickName(),
      userDetails.getProfileUrl(),
      rq);

    return ResponseEntity
      .status(HttpStatus.OK)
      .body(ResponseDto.success("댓글 수정 성공"));
  }

  @Override
  @DeleteMapping("/{commentId}")
  public ResponseEntity<ResponseDto<Void>> deleteComment(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                         @PathVariable Long postId,
                                                         @PathVariable Long commentId) {

    commentService.deleteComment(
      userDetails.getId(),
      commentId);

    return ResponseEntity
      .status(HttpStatus.OK)
      .body(ResponseDto.success("댓글 삭제 성공"));
  }
}
