package inu.unithon.backend.domain.festivalLike.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LikeRequestDto {

    private String title;
    private String imageUrl;
    private String address;
    private String contentId;
}
