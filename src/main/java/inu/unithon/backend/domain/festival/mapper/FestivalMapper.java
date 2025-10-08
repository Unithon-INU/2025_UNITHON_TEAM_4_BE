package inu.unithon.backend.domain.festival.mapper;

import inu.unithon.backend.domain.festival.dto.v2.response.FestivalTranslateResponse;
import inu.unithon.backend.domain.festival.entity.FestivalTranslate;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FestivalMapper {

  FestivalTranslateResponse toResponse(FestivalTranslate festivalTranslate);
}
