package inu.unithon.backend.domain.postLike.dto;

import inu.unithon.backend.domain.post.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostLikeResponseDto {

    private Long postId;
    private String title;
    private String thumbnailUrl;
    private Long likeCount;

    public static PostLikeResponseDto from(Post post) {
        return new PostLikeResponseDto(
                post.getId(),
                post.getTitle(),
                post.getThumbnailUrl(),
                post.getLikes()
        );
    }
}
