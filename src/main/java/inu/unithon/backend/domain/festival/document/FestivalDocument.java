package inu.unithon.backend.domain.festival.document;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Document(indexName = "festivals")
public class FestivalDocument {

  // ElasticSearch에서 사용하는 내부 문서 ID
  @Id
  private String id;

  // 실제 RDB에 있는 Festival 엔티티의 PK
  @Field(type = FieldType.Long)
  private Long festivalId;

  @Field(type = FieldType.Text, analyzer = "nori")
  private String title;

  @Field(type = FieldType.Text, analyzer = "nori")
  private String address;

  @Field(type = FieldType.Text, analyzer = "nori")
  private String content;

  @Field(type = FieldType.Date)
  private LocalDateTime startDate;

  @Field(type = FieldType.Date)
  private LocalDateTime endDate;

  @Builder
  public FestivalDocument(Long festivalId, String title, String address, String content, LocalDateTime startDate, LocalDateTime endDate) {
    this.id = festivalId.toString();
    this.festivalId = festivalId;
    this.title = title;
    this.address = address;
    this.content = content;
    this.startDate = startDate;
    this.endDate = endDate;
  }
}

