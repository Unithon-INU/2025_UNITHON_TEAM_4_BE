package inu.unithon.backend.domain.postLike.controller;

import inu.unithon.backend.domain.member.entity.Member;
import inu.unithon.backend.domain.member.entity.CustomUserDetails;
import inu.unithon.backend.domain.postLike.service.PostLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/postLikes")
public class PostLikeController {

    private final PostLikeService postLikeService;

    private Member getCurrentMember(Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        return userDetails.getMember();
    }

    @PostMapping("/{postId}/like")
    public ResponseEntity<String> toggleLike(@PathVariable Long postId, Authentication authentication) {
        Member member = getCurrentMember(authentication);
        boolean liked = postLikeService.toggleLike(member, postId);
        return ResponseEntity.ok(liked ? "좋아요 완료" : "좋아요 취소");
    }
}
