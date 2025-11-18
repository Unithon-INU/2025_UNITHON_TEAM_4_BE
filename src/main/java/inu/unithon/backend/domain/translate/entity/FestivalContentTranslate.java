package inu.unithon.backend.domain.translate.entity;

import inu.unithon.backend.domain.festival.entity.FestivalContent;
import jakarta.persistence.*;
import inu.unithon.backend.global.entity.BaseEntity;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class FestivalContentTranslate extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Enumerated(EnumType.STRING)
  private TranslateLanguage language;

  private Long contentId;
  private String title;
  private String address;

  @Lob
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

  @Lob
  private String tel;
  private String dist;

  private LocalDateTime startDate;
  private LocalDateTime endDate;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "festival_content_id")
  private FestivalContent festivalContent;

  @Builder
  public FestivalContentTranslate(TranslateLanguage language,
                                  Long contentId,
                                  String title,
                                  String address,
                                  String overview,
                                  String playtime,
                                  String mapx,
                                  String mapy,
                                  String firstImage,
                                  String firstImage2,
                                  String areaCode,
                                  String addr1,
                                  String tel,
                                  String dist,
                                  LocalDateTime startDate,
                                  LocalDateTime endDate,
                                  FestivalContent festivalContent) {
    this.language = language;
    this.contentId = contentId;
    this.title = title;
    this.address = address;
    this.overview = overview;
    this.playtime = playtime;
    this.mapx = mapx;
    this.mapy = mapy;
    this.firstImage = firstImage;
    this.firstImage2 = firstImage2;
    this.areaCode = areaCode;
    this.addr1 = addr1;
    this.tel = tel;
    this.dist = dist;
    this.startDate = startDate;
    this.endDate = endDate;
    this.festivalContent = festivalContent;
  }
}
