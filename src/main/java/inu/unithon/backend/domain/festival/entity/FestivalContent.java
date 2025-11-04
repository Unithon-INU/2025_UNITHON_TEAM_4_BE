package inu.unithon.backend.domain.festival.entity;

import inu.unithon.backend.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FestivalContent extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

    private Long contentId;

    private String title;

    private String address;
    private String content;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    @Column(length = 1000)
    private String overview;
    private String playtime;
    private String mapx;
    private String mapy;
    @Column(length = 1000)
    private String firstImage;
    @Column(length = 1000)
    private String firstImage2;
    private String areaCode;
    private String addr1;
    private String tel;


    @Builder
    public FestivalContent(String title, String address, String content, long contentId,
                           LocalDateTime startDate, LocalDateTime endDate, String overview,
                           String playtime, String mapx, String mapy, String firstImage,
                           String firstImage2, String areaCode, String addr1, String tel) {
        this.title = title;
        this.address = address;
        this.content = content;
        this.startDate = startDate;
        this.endDate = endDate;
        this.contentId = contentId;
        this.overview = overview;
        this.playtime = playtime;
        this.mapx = mapx;
        this.mapy = mapy;
        this.firstImage = firstImage;
        this.firstImage2 = firstImage2;
        this.areaCode = areaCode;
        this.addr1 = addr1;
        this.tel = tel;
    }

    public void updateFromInfo(
            String title, String address,
            LocalDateTime startDate, LocalDateTime endDate,
            String overview, String playtime, String mapx, String mapy,
            String firstImage, String firstImage2, String areaCode, String addr1
    ) {
        this.title = title;
        this.address = address;
        this.startDate = startDate;
        this.endDate = endDate;
        this.overview = overview;
        this.playtime = playtime;
        this.mapx = mapx;
        this.mapy = mapy;
        this.firstImage = firstImage;
        this.firstImage2 = firstImage2;
        this.areaCode = areaCode;
        this.addr1 = addr1;
        this.tel = tel;
    }
}
