package inu.unithon.backend.domain.festival.mapper;

import inu.unithon.backend.domain.festival.document.FestivalDocument;
import inu.unithon.backend.domain.festival.entity.Festival;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class FestivalEsMapperTest {

  private final FestivalEsMapper mapper = FestivalEsMapper.INSTANCE;

  @Test
  void toDocument() throws Exception {
    Festival festival = Festival.builder()
      .title("부평구 축제")
      .address("인천시 부평구")
      .content("부평에서 열리는 문화 축제입니다.")
      .startDate(LocalDateTime.of(2025, 8, 1, 0, 0))
      .endDate(LocalDateTime.of(2025, 8, 2, 0, 0))
      .build();

    // id 리플렉션 세팅
    Field idField = Festival.class.getDeclaredField("id");
    idField.setAccessible(true);
    idField.set(festival, 100L);

    FestivalDocument doc = mapper.toDocument(festival);

    assertThat(doc.getId()).isEqualTo("100");
    assertThat(doc.getFestivalId()).isEqualTo(100L);
    assertThat(doc.getTitle()).isEqualTo("부평구 축제");
    assertThat(doc.getAddress()).isEqualTo("인천시 부평구");
    assertThat(doc.getContent()).isEqualTo("부평에서 열리는 문화 축제입니다.");
    assertThat(doc.getStartDate()).isEqualTo(LocalDateTime.of(2025, 8, 1, 0, 0));
    assertThat(doc.getEndDate()).isEqualTo(LocalDateTime.of(2025, 8, 2, 0, 0));
  }
}
