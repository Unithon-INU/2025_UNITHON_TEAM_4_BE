package inu.unithon.backend.domain.festival.document;

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
@Document(indexName = "festival_translate")
public class FestivalTranslateDocument {

  /**
   * Elasticsearch 문서의 고유 ID
   * -> festivalId + "_" + language 로 구성 (예: "123_kor")
   */
  @Id
  private String id;   // Elasticsearch는 String ID 권장

  @Field(type = FieldType.Keyword)
  private TranslateLanguage language;

  @MultiField(
    mainField = @Field(type = FieldType.Text, analyzer = "nori"), // 기본 한국어
    otherFields = {
      @InnerField(suffix = "eng", type = FieldType.Text, analyzer = "english"),
      @InnerField(suffix = "chn", type = FieldType.Text, analyzer = "smartcn"),
      @InnerField(suffix = "jpn", type = FieldType.Text, analyzer = "kuromoji"),
      @InnerField(suffix = "fra", type = FieldType.Text, analyzer = "french"),
      @InnerField(suffix = "rus", type = FieldType.Text, analyzer = "russian"),
      @InnerField(suffix = "spa", type = FieldType.Text, analyzer = "spanish"),
      @InnerField(suffix = "keyword", type = FieldType.Keyword) // 정렬/정확 검색용
    }
  )
  private String title;

  @Field(type = FieldType.Text, analyzer = "standard")
  private String content;

  @Field(type = FieldType.Text, analyzer = "standard")
  private String address;

  @Field(type = FieldType.Keyword)
  private String imageUrl;

  @Field(type = FieldType.Keyword)
  private String contentId;

  @Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second)
  private LocalDateTime startDate;

  @Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second)
  private LocalDateTime endDate;

  // 원본 Festival ID (관계 대신 ID만 보관)
  @Field(type = FieldType.Keyword)
  private Long festivalId;

  @Builder
  public FestivalTranslateDocument(
    String id,
    TranslateLanguage language,
    String title,
    String content,
    String address,
    String imageUrl,
    String contentId,
    LocalDateTime startDate,
    LocalDateTime endDate,
    Long festivalId
  ) {
    this.id = (id != null) ? id : generateId(festivalId, language);

    this.language = language;
    this.title = title;
    this.content = content;
    this.address = address;
    this.imageUrl = imageUrl;
    this.contentId = contentId;
    this.startDate = startDate;
    this.endDate = endDate;
    this.festivalId = festivalId;
  }

  private static String generateId(Long festivalId, TranslateLanguage language) {
    return festivalId + "_" + language.name();
  }
}
