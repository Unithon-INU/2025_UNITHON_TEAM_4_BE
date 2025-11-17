package inu.unithon.backend.domain.festival.service;

import inu.unithon.backend.domain.translate.entity.FestivalContentTranslate;
import inu.unithon.backend.domain.translate.entity.FestivalTranslate;
import inu.unithon.backend.domain.translate.entity.TranslateLanguage;

public interface FestivalCommandService {
  public void createFestivalTranslate(FestivalTranslate entity);
  public void deleteFestivalTranslate(Long contentId, TranslateLanguage language);
  public void createFestivalContentTranslate(FestivalContentTranslate entity);
  public void deleteFestivalContentTranslate(Long contentId,TranslateLanguage language);
}
