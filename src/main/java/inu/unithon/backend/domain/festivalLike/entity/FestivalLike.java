package inu.unithon.backend.domain.festivalLike.entity;

import inu.unithon.backend.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor

public class FestivalLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    // 축제 고유 식별자 (공공 API의 contentId)
    private String contentId;

    private String title;
    private String imageUrl;
    private String address;

    private LocalDateTime bookmarkedAt;

    @Builder
    public FestivalLike(Member member, String contentId, String title, String imageUrl, String address) {
        this.member = member;
        this.contentId = contentId;
        this.title = title;
        this.imageUrl = imageUrl;
        this.address = address;
        this.bookmarkedAt = LocalDateTime.now();
    }
}
