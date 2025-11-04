package inu.unithon.backend.domain.festival.service;

import inu.unithon.backend.domain.festival.dto.v1.FestivalDto;

import java.util.List;

public interface FestivalSaveService {
  void saveFestivalList(List<FestivalDto> dtoList);

}
