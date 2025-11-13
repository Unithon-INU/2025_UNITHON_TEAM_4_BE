package inu.unithon.backend.domain.translate.entity;

import lombok.Getter;

@Getter
public enum TranslateLanguage {
  kor("ko"), // 한국어
  jpn("ja"), // 일본어
  chn("zh-CN"), // 중국어
  eng("en"),  // 영어
  fra("fr"),
  rus("ru"),
  spa("es");

  private final String code;

  TranslateLanguage(String code) {
    this.code = code;
  }

}
