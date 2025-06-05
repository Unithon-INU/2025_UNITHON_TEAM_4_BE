package inu.unithon.backend.domain.comment.controller.docs;

import inu.unithon.backend.domain.comment.dto.req.CommentCreateRequest;
import inu.unithon.backend.domain.comment.dto.req.CommentUpdateRequest;
import inu.unithon.backend.domain.member.entity.CustomUserDetails;
import inu.unithon.backend.global.response.ResponseDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface CommentControllerSpecification {

  /**
   * 댓글 남기기
   * @param userDetails
   * @param postId
   * @param rq
   * @return
   */
  ResponseEntity<ResponseDto<Void>> createComment(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                  @PathVariable Long postId,
                                                  @Valid @RequestBody CommentCreateRequest rq);

  /**
   * 댓글 수정
   * @param userDetails
   * @param postId
   * @param commentId
   * @param rq
   * @return
   */
  ResponseEntity<ResponseDto<Void>> updateComment(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                  @PathVariable Long postId,
                                                  @PathVariable Long commentId,
                                                  @RequestBody @Valid CommentUpdateRequest rq);

  /**
   * 댓글 삭제
   * @param userDetails
   * @param postId
   * @param commentId
   * @return
   */
  ResponseEntity<ResponseDto<Void>> deleteComment(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                  @PathVariable Long postId,
                                                  @PathVariable Long commentId);

}
