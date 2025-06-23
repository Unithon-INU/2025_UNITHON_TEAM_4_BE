package inu.unithon.backend.domain.festivalLike.controller;

import inu.unithon.backend.domain.festivalLike.dto.LikeRequestDto;
import inu.unithon.backend.domain.festivalLike.dto.LikeResponseDto;
import inu.unithon.backend.domain.festivalLike.service.FestivalLikeService;
import inu.unithon.backend.domain.member.entity.Member;
import inu.unithon.backend.domain.member.entity.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/festivals")
public class FestivalLikeController {

    private final FestivalLikeService likeService;

    private Member getCurrentMember(Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        return userDetails.getMember();
    }

    // ✅ 좋아요 토글 (좋아요 추가 또는 취소)
    @PostMapping("/{contentId}/like")
    public ResponseEntity<String> toggleLike(
            @PathVariable String contentId,
            @RequestBody LikeRequestDto dto,
            Authentication authentication
    ) {
        Member member = getCurrentMember(authentication);
        boolean liked = likeService.addLike(member, contentId, dto.getTitle(), dto.getImageUrl(), dto.getAddress());

        String message = liked ? "좋아요 추가됨" : "좋아요 취소됨";
        return ResponseEntity.ok(message);
    }


    //  내가 좋아요 누른 축제 목록
    @GetMapping("/likes")
    public ResponseEntity<List<LikeResponseDto>> getLikes(Authentication authentication) {
        Member member = getCurrentMember(authentication);
        List<LikeResponseDto> result = likeService.getLikedFestivals(member).stream()
                .map(LikeResponseDto::from)
                .toList();
        return ResponseEntity.ok(result);
    }
}

