package inu.unithon.backend.domain.festival.dto.v2.request;

import inu.unithon.backend.domain.festival.entity.TranslateLanguage;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;

@Getter
public class FestivalTranslateSearchRequest {

  @Enumerated(EnumType.STRING)
  private TranslateLanguage lang;
  private String keyword;
}
