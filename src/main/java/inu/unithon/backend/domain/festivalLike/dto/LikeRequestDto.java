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

    // 기본 생성자 + Getter만 있으면 Jackson이 자동 역직렬화해줌
}
