package inu.unithon.backend.domain.comment.service;

import inu.unithon.backend.domain.comment.dto.req.CommentCreateRequest;
import inu.unithon.backend.domain.comment.dto.req.CommentUpdateRequest;

public interface CommentService {

  /**
   * 댓글 남기기
   * @param memberId
   * @param memberName
   * @param profileUrl
   * @param postId
   * @param rq
   */
  void addComment(Long memberId,
                  String memberName,
                  String profileUrl,
                  Long postId,
                  CommentCreateRequest rq);

  /**
   * 댓글 수정
   * @param memberId
   * @param memberName
   * @param profileUrl
   * @param postId
   * @param commentId
   * @param rq
   */
  void updateComment(Long memberId,
                     String memberName,
                     String profileUrl,
                     Long postId,
                     Long commentId,
                     CommentUpdateRequest rq);

  /**
   * 댓글 삭제
   * @param memberId
   * @param commentId
   */
  void deleteComment(Long memberId,
                     Long commentId);
}
