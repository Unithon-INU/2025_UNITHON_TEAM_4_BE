package inu.unithon.backend.domain.festival.entity;

import inu.unithon.backend.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FestivalContent extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private long contentId;
    private String imageUrl;
    private String address;
    private String content;
    //    private String startDate;
    private LocalDateTime startDate;
    //    private String endDate;
    private LocalDateTime endDate;


//        @OneToOne(fetch = FetchType.LAZY)
//        @JoinColumn(name = "festival_id")  // 양방향 생각했었음..
//        private Festival festival;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "festival_id")
    private Festival festival;

    @OneToMany(mappedBy = "festivalContent", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<FestivalTranslateContent> festivalTranslateContents = new ArrayList<>();


    //        @Builder
//        public FestivalContent(String title, String imageUrl, String address, String content,
//                               String startDate, String endDate) {
//            this.title = title;
//            this.imageUrl = imageUrl;
//            this.address = address;
//            this.content = content;
//            this.startDate = startDate;
//            this.endDate = endDate;
//        }
    @Builder
    public FestivalContent(String title, String imageUrl, String address, String content, long contentId,
                           LocalDateTime startDate, LocalDateTime endDate) {
        this.title = title;
        this.imageUrl = imageUrl;
        this.address = address;
        this.content = content;
        this.startDate = startDate;
        this.endDate = endDate;
        this.contentId = contentId;
    }
}
