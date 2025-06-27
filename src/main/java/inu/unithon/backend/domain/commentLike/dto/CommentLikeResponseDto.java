package inu.unithon.backend.domain.commentLike.dto;

import inu.unithon.backend.domain.comment.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentLikeResponseDto {

    private Long commentId;
    private String content;
    private String memberName;
    private Long likeCount;

    public static CommentLikeResponseDto from(Comment comment) {
        return new CommentLikeResponseDto(
                comment.getId(),
                comment.getContent(),
                comment.getMemberName(),
                comment.getLikes()
        );
    }
}
