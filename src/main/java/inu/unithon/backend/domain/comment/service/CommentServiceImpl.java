package inu.unithon.backend.domain.comment.service;

import inu.unithon.backend.domain.comment.dto.req.CommentCreateRequest;
import inu.unithon.backend.domain.comment.dto.req.CommentUpdateRequest;
import inu.unithon.backend.domain.comment.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

  private final CommentRepository commentRepository;

  @Override
  public void addComment(Long memberId,
                         Long postId,
                         CommentCreateRequest rq) {

  }

  @Override
  public void updateComment(Long memberId,
                            Long postId,
                            Long commentId,
                            CommentUpdateRequest rq) {

  }

  @Override
  public void deleteComment(Long memberId,
                            Long postId,
                            Long commentId) {

  }
}
