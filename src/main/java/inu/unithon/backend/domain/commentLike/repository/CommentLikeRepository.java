package inu.unithon.backend.domain.commentLike.repository;

import inu.unithon.backend.domain.comment.entity.Comment;
import inu.unithon.backend.domain.commentLike.entity.CommentLike;
import inu.unithon.backend.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
import java.util.Optional;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
    Optional<CommentLike> findByMemberAndComment(Member member, Comment comment);
    boolean existsByMemberAndComment(Member member, Comment comment);
    List<CommentLike> findAllByMember(Member member);
}
