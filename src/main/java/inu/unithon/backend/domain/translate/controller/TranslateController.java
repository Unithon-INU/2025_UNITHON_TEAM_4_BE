package inu.unithon.backend.domain.translate.controller;

import inu.unithon.backend.domain.translate.client.PapagoClient;
import inu.unithon.backend.domain.translate.service.TranslationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/translate")
@RequiredArgsConstructor
public class TranslateController implements TranslateControllerSpecification{

  private final TranslationService translationService;
  private final PapagoClient papagoClient;

  @Override
  public String translateText(String text, String source, String target) {
    return papagoClient.translate(text, source, target);
  }
}
