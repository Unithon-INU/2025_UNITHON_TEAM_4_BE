package inu.unithon.backend.domain.translate.entity;

import inu.unithon.backend.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 번역 기록을 남겨서 디버깅 / 통계용으로 사용
 * 요청 횟수 및 캐싱용
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TranslationLog extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String sourceLang;
  private String targetLang;
  @Column(length = 5000)
  private String originalText;
  @Column(length = 5000)
  private String translatedText;

  @Builder
  public TranslationLog(String sourceLang, String targetLang, String originalText, String translatedText) {
    this.sourceLang = sourceLang;
    this.targetLang = targetLang;
    this.originalText = originalText;
    this.translatedText = translatedText;
  }
}

