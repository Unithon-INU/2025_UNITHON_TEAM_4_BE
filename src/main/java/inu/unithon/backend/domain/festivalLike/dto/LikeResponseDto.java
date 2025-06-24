package inu.unithon.backend.domain.festivalLike.dto;

import inu.unithon.backend.domain.festivalLike.entity.FestivalLike;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LikeResponseDto {

    private String contentId;
    private String title;
    private String imageUrl;
    private String address;

    public static LikeResponseDto from(FestivalLike like) {
        return new LikeResponseDto(
                like.getContentId(),
                like.getTitle(),
                like.getImageUrl(),
                like.getAddress()
        );
    }
}
