package inu.unithon.backend.domain.translate.document;

import inu.unithon.backend.domain.translate.entity.TranslateLanguage;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Document(indexName = "festival_content_translate")
public class FestivalContentTranslateDocument {

  /** ES 문서 ID = contentId_language */
  @Id
  private String id;

  @Field(type = FieldType.Keyword)
  private TranslateLanguage language;

  @Field(type = FieldType.Keyword)
  private Long contentId;

  /** 제목 검색 — 한국어 + MultiLang */
  @MultiField(
    mainField = @Field(type = FieldType.Text, analyzer = "nori"),
    otherFields = {
      @InnerField(suffix = "eng", type = FieldType.Text, analyzer = "english"),
      @InnerField(suffix = "chn", type = FieldType.Text, analyzer = "smartcn"),
      @InnerField(suffix = "jpn", type = FieldType.Text, analyzer = "kuromoji"),
      @InnerField(suffix = "fra", type = FieldType.Text, analyzer = "french"),
      @InnerField(suffix = "rus", type = FieldType.Text, analyzer = "russian"),
      @InnerField(suffix = "spa", type = FieldType.Text, analyzer = "spanish"),
      @InnerField(suffix = "keyword", type = FieldType.Keyword)
    }
  )
  private String title;

  @Field(type = FieldType.Text, analyzer = "nori")
  private String address;

  @Field(type = FieldType.Text, analyzer = "nori")
  private String overview;

  @Field(type = FieldType.Keyword)
  private String playtime;

  @Field(type = FieldType.Keyword)
  private String mapx;

  @Field(type = FieldType.Keyword)
  private String mapy;

  @Field(type = FieldType.Keyword)
  private String firstImage;

  @Field(type = FieldType.Keyword)
  private String firstImage2;

  @Field(type = FieldType.Keyword)
  private String areaCode;

  @Field(type = FieldType.Text, analyzer = "nori")
  private String addr1;

  @Field(type = FieldType.Keyword)
  private String tel;

  @Field(type = FieldType.Keyword)
  private String dist;

  @Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second)
  private LocalDateTime startDate;

  @Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second)
  private LocalDateTime endDate;

  /** 원본 FestivalContent 참조 대신, ID만 저장 */
  @Field(type = FieldType.Keyword)
  private Long festivalContentId;

  @Builder
  public FestivalContentTranslateDocument(
    String id,
    Long contentId,
    TranslateLanguage language,
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
    Long festivalContentId
  ) {
    this.id = (id != null) ? id : generateId(contentId, language);

    this.contentId = contentId;
    this.language = language;

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

    this.festivalContentId = festivalContentId;
  }

  private static String generateId(Long contentId, TranslateLanguage lang) {
    return contentId + "_" + lang.name();
  }
}
