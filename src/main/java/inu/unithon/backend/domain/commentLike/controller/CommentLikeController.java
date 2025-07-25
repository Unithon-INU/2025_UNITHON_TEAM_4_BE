package inu.unithon.backend.domain.commentLike.controller;

import inu.unithon.backend.domain.comment.entity.Comment;
import inu.unithon.backend.domain.comment.repository.CommentRepository;
import inu.unithon.backend.domain.commentLike.dto.CommentLikeResponseDto;
import inu.unithon.backend.domain.commentLike.service.CommentLikeService;
import inu.unithon.backend.domain.member.entity.Member;
import inu.unithon.backend.domain.member.entity.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import org.springframework.security.core.Authentication;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comments")
public class CommentLikeController {

    private final CommentLikeService commentLikeService;
    private final CommentRepository commentRepository;

    private Member getCurrentMember(Authentication authentication) {
        return ((CustomUserDetails) authentication.getPrincipal()).getMember();
    }

    @PostMapping("/{commentId}/like")
    public ResponseEntity<CommentLikeResponseDto> toggleLike(
            @PathVariable Long commentId,
            Authentication authentication
    ) {
        Member member = getCurrentMember(authentication);
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("comment is not found"));

        boolean liked = commentLikeService.toggleLike(member, comment);
        return ResponseEntity.ok(CommentLikeResponseDto.from(comment));
    }

    @GetMapping("/myLikes")
    public ResponseEntity<List<CommentLikeResponseDto>> getLikedComments(Authentication authentication) {
        Member member = getCurrentMember(authentication);
        return ResponseEntity.ok(commentLikeService.getLikedComments(member));
    }
}
