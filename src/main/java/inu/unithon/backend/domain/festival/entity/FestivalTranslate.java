package inu.unithon.backend.domain.festival.entity;

import jakarta.persistence.*;
import inu.unithon.backend.global.entity.BaseEntity;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FestivalTranslate extends BaseEntity {

    @Id
    private long contentId;

    private String language;
    private String title;
    @Column(length = 1000)
    private String imageUrl;
    private String address;
    private String content;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "festival_id")
    private Festival festival;


    @Builder
    public FestivalTranslate(String language, String title, String imageUrl, String address, long contentId, String content, LocalDateTime startDate, LocalDateTime endDate, Festival festival) {
        this.language = language;
        this.title = title;
        this.imageUrl = imageUrl;
        this.address = address;
        this.contentId = contentId;
        this.content = content;
        this.startDate = startDate;
        this.endDate = endDate;
        this.festival= festival;
    }
}
