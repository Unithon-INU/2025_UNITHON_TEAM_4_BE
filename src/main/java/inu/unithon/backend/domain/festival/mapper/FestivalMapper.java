package inu.unithon.backend.domain.festival.mapper;

import inu.unithon.backend.domain.festival.document.FestivalTranslateDocument;
import inu.unithon.backend.domain.festival.dto.v2.response.FestivalTranslateResponse;
import inu.unithon.backend.domain.festival.entity.FestivalTranslate;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FestivalMapper {

  FestivalTranslateResponse toResponse(FestivalTranslate festivalTranslate);
  FestivalTranslateResponse toResponseFromDocument(FestivalTranslateDocument document);

  @Mapping(target = "id", expression = "java(festivalTranslate.getFestival().getId() + \"_\" + festivalTranslate.getLanguage().name())")
  @Mapping(target = "festivalId", expression = "java(festivalTranslate.getFestival() != null ? festivalTranslate.getFestival().getId() : null)")
  FestivalTranslateDocument toDocumentFromFestivalTranslate(FestivalTranslate festivalTranslate);
}
