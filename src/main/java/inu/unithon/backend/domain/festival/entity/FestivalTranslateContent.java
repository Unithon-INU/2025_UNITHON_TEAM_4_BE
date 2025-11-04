package inu.unithon.backend.domain.festival.entity;

import jakarta.persistence.*;
import inu.unithon.backend.global.entity.BaseEntity;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FestivalTranslateContent extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TranslateLanguage language;
    private String title;
    private String imageUrl;
    private String address;
    private Long contentId;
    private String content;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "festivalContent_id")
    private FestivalContent festivalContent;

    @Builder
    public FestivalTranslateContent(TranslateLanguage language,
                                    String title,
                                    String imageUrl,
                                    String address,
                                    Long contentId,
                                    String content,
                                    LocalDateTime startDate,
                                    LocalDateTime endDate,
                                    FestivalContent festivalContent) {

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
