package inu.unithon.backend.domain.comment.service;

import inu.unithon.backend.domain.comment.dto.req.CommentCreateRequest;
import inu.unithon.backend.domain.comment.dto.req.CommentUpdateRequest;
import inu.unithon.backend.domain.comment.entity.Comment;
import inu.unithon.backend.domain.comment.repository.CommentRepository;
import inu.unithon.backend.domain.post.entity.Post;
import inu.unithon.backend.domain.post.repository.PostRepository;
import inu.unithon.backend.global.exception.CustomException;
import inu.unithon.backend.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

  private final CommentRepository commentRepository;
  private final PostRepository postRepository;

  @Override
  public void addComment(Long memberId,
                         String memberName,
                         String profileUrl,
                         CommentCreateRequest rq) {

    Post post = postRepository.findById(rq.getPostId())
      .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

    Comment comment = Comment.builder()
      .content(rq.getContent())
      .post(post)
      .memberId(memberId)
      .memberName(memberName)
      .memberProfileImageUrl(profileUrl)
      .build();

    log.info("Add comment {}", comment);
    commentRepository.save(comment);
  }

  @Override
  public void updateComment(Long memberId,
                            String memberName,
                            String profileUrl,
                            CommentUpdateRequest rq) {

    Comment comment = commentRepository.findById(rq.getCommentId())
      .orElseThrow(()-> new CustomException(ErrorCode.COMMENT_NOT_FOUND));

    // 검증 : memberId, commentId 일치인지
    if (!Objects.equals(comment.getMemberId(), memberId)) throw new CustomException(ErrorCode.FORBIDDEN_401);

    log.info("Update comment {}", comment);
    comment.updateContent(rq.getComment());
  }

  @Override
  public void deleteComment(Long memberId,
                            Long commentId) {

    Comment comment = commentRepository.findById(commentId)
      .orElseThrow(()-> new CustomException(ErrorCode.COMMENT_NOT_FOUND));

    if (!Objects.equals(comment.getMemberId(), memberId)) throw new CustomException(ErrorCode.FORBIDDEN_402);

    log.info("Delete comment {}", comment);
    commentRepository.deleteById(commentId);
  }
}
