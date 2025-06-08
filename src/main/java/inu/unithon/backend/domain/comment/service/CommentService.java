package inu.unithon.backend.domain.comment.service;

import inu.unithon.backend.domain.comment.dto.req.CommentCreateRequest;
import inu.unithon.backend.domain.comment.dto.req.CommentUpdateRequest;

public interface CommentService {

  /**
   * 댓글 남기기
   * @param memberId
   * @param memberName
   * @param profileUrl
   * @param rq
   */
  void addComment(Long memberId,
                  String memberName,
                  String profileUrl,
                  CommentCreateRequest rq);

  /**
   * 댓글 수정
   * @param memberId
   * @param memberName
   * @param profileUrl
   * @param rq
   */
  void updateComment(Long memberId,
                     String memberName,
                     String profileUrl,
                     CommentUpdateRequest rq);

  /**
   * 댓글 삭제
   * @param memberId
   * @param commentId
   */
  void deleteComment(Long memberId,
                     Long commentId);
}
