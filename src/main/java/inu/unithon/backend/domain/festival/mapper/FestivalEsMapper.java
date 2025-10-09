package inu.unithon.backend.domain.festival.mapper;

import inu.unithon.backend.domain.festival.entity.Festival;
import inu.unithon.backend.domain.festival.document.FestivalDocument;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * Mapper
 * Document ↔ Entity
 */
@Mapper(componentModel = "spring")
public interface FestivalEsMapper {

  FestivalEsMapper INSTANCE = Mappers.getMapper(FestivalEsMapper.class);

  @Mapping(target = "festivalId", source = "id")
  FestivalDocument toDocument(Festival festival);

//  @Mapping(target = "id", ignore = true)
//  Festival toEntity(FestivalDocument document);
}
