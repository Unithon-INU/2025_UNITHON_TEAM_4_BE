package inu.unithon.backend.domain.festival.entity;

import jakarta.persistence.*;
import inu.unithon.backend.global.entity.BaseEntity;
import lombok.*;
import java.util.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Festival extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String imageUrl;
    private String address;
    private String contentId;
    private String content;
    private String startDate;
    private String endDate;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "festival_content_id")
    private FestivalContent festivalContent;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "festival_id")
    private List<FestivalTranslate> festivalTranslates = new ArrayList<>();

    @Builder
    public Festival(String title, String imageUrl, String address, String contentId,
                    String content, String startDate, String endDate, FestivalContent festivalContent, List<FestivalTranslate> festivalTranslates) {
        this.title = title;
        this.imageUrl = imageUrl;
        this.address = address;
        this.contentId = contentId;
        this.content = content;
        this.startDate = startDate;
        this.endDate = endDate;
        this.festivalContent = festivalContent;
        this.festivalTranslates = festivalTranslates;
    }
}
