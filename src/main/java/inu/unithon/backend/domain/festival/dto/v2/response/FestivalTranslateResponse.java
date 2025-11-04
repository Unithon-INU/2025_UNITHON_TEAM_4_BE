package inu.unithon.backend.domain.festival.dto.v2.response;

import inu.unithon.backend.domain.festival.entity.TranslateLanguage;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class FestivalTranslateResponse {

  private Long id;

  @Enumerated(EnumType.STRING)
  private TranslateLanguage language;

  private String title;
  private String imageUrl;
  private String address;
  private String contentId;
  private String content;
  private LocalDateTime startDate;
  private LocalDateTime endDate;
}
