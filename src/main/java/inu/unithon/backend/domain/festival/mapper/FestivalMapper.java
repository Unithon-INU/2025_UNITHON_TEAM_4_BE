package inu.unithon.backend.domain.festival.mapper;

import inu.unithon.backend.domain.festival.document.FestivalTranslateDocument;
import inu.unithon.backend.domain.festival.dto.FestivalDto;
import inu.unithon.backend.domain.festival.dto.FestivalInfoResponseDto;
import inu.unithon.backend.domain.festival.dto.FestivalIntroResponseDto;
import inu.unithon.backend.domain.festival.entity.Festival;
import inu.unithon.backend.domain.festival.entity.FestivalContentTranslate;
import inu.unithon.backend.domain.festival.entity.FestivalTranslate;
import jakarta.persistence.Column;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.time.LocalDateTime;
import java.util.List;


@Mapper(componentModel = "spring")
public interface FestivalMapper {

  @Mapping(target = "id", expression = "java(festivalTranslate.getFestival().getId() + \"_\" + festivalTranslate.getLanguage().name())")
  @Mapping(target = "festivalId", expression = "java(festivalTranslate.getFestival() != null ? festivalTranslate.getFestival().getId() : null)")
  FestivalTranslateDocument toDocumentFromFestivalTranslate(FestivalTranslate festivalTranslate);
  /**
   * FestivalTranslate → FestivalDto 변환
   * (축제 기본 정보)
   */
  @Mappings({
          @Mapping(source = "title", target = "title", defaultValue = " "),
          @Mapping(source = "address", target = "addr1", defaultValue = " "),
          @Mapping(target = "addr2", constant = " "),
          @Mapping(source = "imageUrl", target = "firstimage", defaultValue = " "),
          @Mapping(source = "contentId", target = "contentid"),
          @Mapping(target = "areacode", constant = " "),
          @Mapping(target = "contenttypeid", constant = " "),
          @Mapping(target = "createdtime", constant = " "),
          @Mapping(target = "firstimage2", constant = " "),
          @Mapping(target = "mapx", constant = " "),
          @Mapping(target = "mapy", constant = " "),
          @Mapping(target = "modifiedtime", constant = " "),
          @Mapping(target = "tel", constant = " "),
          @Mapping(target = "zipcode", constant = " "),
          @Mapping(source = "content", target = "overview", defaultValue = " "),
          @Mapping(target = "dist", constant = " "),
          @Mapping(source = "startDate", target = "eventstartdate", dateFormat = "yyyy-MM-dd"),
          @Mapping(source = "endDate", target = "eventenddate", dateFormat = "yyyy-MM-dd"),
          @Mapping(target = "item", ignore = true)
  })
  FestivalDto toDtoFromFestival(FestivalTranslate festival);

  /**
   * Festival → FestivalDto 변환
   * (한국어 원본)
   */
  @Mappings({
          @Mapping(source = "title", target = "title", defaultValue = " "),
          @Mapping(source = "address", target = "addr1", defaultValue = " "),
          @Mapping(source = "imageUrl", target = "firstimage", defaultValue = " "),
          @Mapping(source = "contentId", target = "contentid"),
          @Mapping(target = "createdtime", constant = " "),
          @Mapping(target = "modifiedtime", constant = " "),
          @Mapping(source = "startDate", target = "eventstartdate", dateFormat = "yyyy-MM-dd"),
          @Mapping(source = "endDate", target = "eventenddate", dateFormat = "yyyy-MM-dd"),
  })
  FestivalDto toDtoFromOriginalFestival(Festival festival);

  /**
   * FestivalContentTranslate → FestivalDto 변환
   * (상세 컨텐츠 정보)
   */
  @Mappings({
          @Mapping(source = "addr1", target = "addr2", defaultValue = " "),
          @Mapping(source = "areaCode", target = "areacode", defaultValue = " "),
          @Mapping(source = "contentId", target = "contentid"),
          @Mapping(target = "contenttypeid", constant = " "),
          @Mapping(target = "createdtime", constant = " "),
          @Mapping(source = "firstImage", target = "firstimage", defaultValue = " "),
          @Mapping(source = "firstImage2", target = "firstimage2", defaultValue = " "),
          @Mapping(source = "mapx", target = "mapx", defaultValue = " "),
          @Mapping(source = "mapy", target = "mapy", defaultValue = " "),
          @Mapping(target = "modifiedtime", constant = " "),
          @Mapping(source = "tel", target = "tel", defaultValue = " "),
          @Mapping(target = "zipcode", constant = " "),
          @Mapping(source = "overview", target = "overview", defaultValue = " "),
          @Mapping(source = "playtime", target = "dist", defaultValue = " "),
          @Mapping(source = "startDate", target = "eventstartdate", dateFormat = "yyyy-MM-dd"),
          @Mapping(source = "endDate", target = "eventenddate", dateFormat = "yyyy-MM-dd"),
          @Mapping(target = "title", defaultValue = " "),
          @Mapping(target = "addr1", constant = " "),
          @Mapping(target = "item", ignore = true)
  })
  FestivalDto toDtoFromFestivalContent(FestivalContentTranslate content);

  // FestivalContentTranslate → FestivalIntroResponseDto
  default FestivalIntroResponseDto toIntroFromFestivalContent(FestivalContentTranslate content) {
    try {
      FestivalDto dto = toDtoFromFestivalContent(content);

      FestivalIntroResponseDto.Body.Items items = new FestivalIntroResponseDto.Body.Items();
      FieldUtils.writeField(items, "item", List.of(dto), true);

      FestivalIntroResponseDto.Body body = new FestivalIntroResponseDto.Body();
      FieldUtils.writeField(body, "items", items, true);
      FieldUtils.writeField(body, "numOfRows", 1, true);
      FieldUtils.writeField(body, "pageNo", 1, true);
      FieldUtils.writeField(body, "totalCount", 1, true);

      FestivalIntroResponseDto.Header header = new FestivalIntroResponseDto.Header();
      FieldUtils.writeField(header, "resultCode", "0000", true);
      FieldUtils.writeField(header, "resultMsg", "OK", true);

      FestivalIntroResponseDto.Response response = new FestivalIntroResponseDto.Response();
      FieldUtils.writeField(response, "header", header, true);
      FieldUtils.writeField(response, "body", body, true);

      return FestivalIntroResponseDto.builder()
              .response(response)
              .build();

    } catch (IllegalAccessException e) {
      throw new RuntimeException(e);
    }
  }

  // FestivalContentTranslate → FestivalInfoResponseDto
  default FestivalInfoResponseDto toInfoFromFestivalContent(FestivalContentTranslate content) {
    try {
      FestivalDto dto = toDtoFromFestivalContent(content);

      FestivalInfoResponseDto.Body.Items items = new FestivalInfoResponseDto.Body.Items();
      FieldUtils.writeField(items, "item", List.of(dto), true);

      FestivalInfoResponseDto.Body body = new FestivalInfoResponseDto.Body();
      FieldUtils.writeField(body, "items", items, true);
      FieldUtils.writeField(body, "numOfRows", 1, true);
      FieldUtils.writeField(body, "pageNo", 1, true);
      FieldUtils.writeField(body, "totalCount", 1, true);

      FestivalInfoResponseDto.Header header = new FestivalInfoResponseDto.Header();
      FieldUtils.writeField(header, "resultCode", "0000", true);
      FieldUtils.writeField(header, "resultMsg", "OK", true);

      FestivalInfoResponseDto.Response response = new FestivalInfoResponseDto.Response();
      FieldUtils.writeField(response, "header", header, true);
      FieldUtils.writeField(response, "body", body, true);

      return FestivalInfoResponseDto.builder()
              .response(response)
              .build();

    } catch (IllegalAccessException e) {
      throw new RuntimeException(e);
    }
  }
}