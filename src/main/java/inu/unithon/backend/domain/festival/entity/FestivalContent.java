    package inu.unithon.backend.domain.festival.entity;

    import inu.unithon.backend.global.entity.BaseEntity;
    import jakarta.persistence.*;
    import lombok.*;

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
        private String imageUrl;
        private String address;
        private String content;
        private String startDate;
        private String endDate;


        @OneToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "festival_id")  // FK는 여기 있음
        private Festival festival;

        @OneToMany(mappedBy = "festivalContent", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
        private List<FestivalTranslateContent> festivalTranslateContents = new ArrayList<>();

        @Builder
        public FestivalContent(String title, String imageUrl, String address, String content,
                               String startDate, String endDate) {
            this.title = title;
            this.imageUrl = imageUrl;
            this.address = address;
            this.content = content;
            this.startDate = startDate;
            this.endDate = endDate;
        }

    }
