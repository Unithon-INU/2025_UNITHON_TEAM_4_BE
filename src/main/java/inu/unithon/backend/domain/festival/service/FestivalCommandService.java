package inu.unithon.backend.domain.festival.service;

import inu.unithon.backend.domain.translate.entity.FestivalTranslate;

public interface FestivalCommandService {
  public void createFestivalTranslate(FestivalTranslate entity);
  public void deleteFestivalTranslate(Long id);
}
