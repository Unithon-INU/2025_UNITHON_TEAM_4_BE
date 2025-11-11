package inu.unithon.backend.domain.like.commentLike.service;

import inu.unithon.backend.domain.comment.entity.Comment;
import inu.unithon.backend.domain.like.commentLike.entity.CommentLike;
import inu.unithon.backend.domain.like.commentLike.repository.CommentLikeRepository;
import inu.unithon.backend.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import inu.unithon.backend.domain.like.commentLike.dto.CommentLikeResponseDto;


import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentLikeService {

    private final CommentLikeRepository commentLikeRepository;

    @Transactional
    public boolean toggleLike(Member member, Comment comment) {
        Optional<CommentLike> existing = commentLikeRepository.findByMemberAndComment(member, comment);

        if (existing.isPresent()) {
            commentLikeRepository.delete(existing.get());
            comment.decreaseLikeCount();
            return false;
        } else {
            commentLikeRepository.save(CommentLike.builder()
                    .member(member)
                    .comment(comment)
                    .build());
            comment.increaseLikeCount();
            return true;
        }
    }

    public List<CommentLikeResponseDto> getLikedComments(Member member) {
        return commentLikeRepository.findAllByMember(member).stream()
                .map(commentLike -> CommentLikeResponseDto.from(commentLike.getComment()))
                .toList();
    }
}
