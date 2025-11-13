package inu.unithon.backend.domain.festival.document;

import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Document(indexName = "festival_content_translate")
public class FestivalContentTranslateDocument {
  /**
   * Elasticsearch 문서의 고유 ID
   * -> festivalId + "_" + language 로 구성 (예: "123_kor")
   */
  @Id
  private String id;   // Elasticsearch는 String ID 권장
}
