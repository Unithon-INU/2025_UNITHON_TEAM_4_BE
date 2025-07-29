package inu.unithon.backend.domain.festival.entity;

import jakarta.persistence.*;
import inu.unithon.backend.global.entity.BaseEntity;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FestivalTranslateContent extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String language;
    private String title;
    private String imageUrl;
    private String address;
    private long contentId;
    private String content;
    private String startDate;
    private String endDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "festivalContent_id")
    private FestivalContent festivalContent;

    @Builder
    public FestivalTranslateContent(String language, String title, String imageUrl, String address,
                                    long contentId, String content, String startDate, String endDate, FestivalContent festivalContent) {
        this.language = language;
        this.title = title;
        this.imageUrl = imageUrl;
        this.address = address;
        this.contentId = contentId;
        this.content = content;
        this.startDate = startDate;
        this.endDate = endDate;
        this.festivalContent = festivalContent;
    }
}
