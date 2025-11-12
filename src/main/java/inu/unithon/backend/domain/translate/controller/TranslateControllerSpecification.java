package inu.unithon.backend.domain.translate.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

public interface TranslateControllerSpecification {
  /**
   *
   * @param text : 번역 할 문장
   * @param source : 번역 할 문장의 언어
   * @param target : 번역 할 문장의 타깃 언어
   * @return : 번역 된 문장
   */
  @GetMapping
  String translateText(
    @RequestParam String text,
    @RequestParam(defaultValue = "ko") String source,
    @RequestParam(defaultValue = "en") String target
  );
}
