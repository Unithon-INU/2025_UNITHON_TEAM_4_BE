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
    private String contentId;
    private String content;
    private String startDate;
    private String endDate;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "festivalContent_id") // 양방향 생각했었음..
//    private FestivalContent festivalContent;

    @Builder
    public FestivalTranslateContent(String language, String title, String imageUrl, String address, String contentId, String content, String startDate, String endDate) {
        this.language = language;
        this.title = title;
        this.imageUrl = imageUrl;
        this.address = address;
        this.contentId = contentId;
        this.content = content;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
