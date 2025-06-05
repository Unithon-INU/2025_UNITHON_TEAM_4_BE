package inu.unithon.backend.domain.comment.service;

import inu.unithon.backend.domain.comment.dto.req.CommentCreateRequest;
import inu.unithon.backend.domain.comment.dto.req.CommentUpdateRequest;

public interface CommentService {

  /**
   * 댓글 남기기
   * @param memberId
   * @param postId
   * @param rq
   */
  void addComment(Long memberId,
                  Long postId,
                  CommentCreateRequest rq);

  /**
   * 댓글 수정
   * @param memberId
   * @param postId
   * @param commentId
   * @param rq
   */
  void updateComment(Long memberId,
                     Long postId,
                     Long commentId,
                     CommentUpdateRequest rq);

  /**
   * 댓글 삭제
   * @param memberId
   * @param postId
   * @param commentId
   */
  void deleteComment(Long memberId,
                     Long postId,
                     Long commentId);
}
