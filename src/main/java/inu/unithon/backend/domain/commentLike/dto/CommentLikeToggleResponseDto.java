package inu.unithon.backend.domain.commentLike.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentLikeToggleResponseDto {
    private boolean liked;    // true = 좋아요 추가됨, false = 해제됨
    private Long likeCount;   // 현재 댓글 좋아요 수
}
