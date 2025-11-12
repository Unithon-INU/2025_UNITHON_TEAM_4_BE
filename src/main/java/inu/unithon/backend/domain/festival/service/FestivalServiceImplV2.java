package inu.unithon.backend.domain.festival.service;

import inu.unithon.backend.domain.festival.dto.FestivalDto;
import inu.unithon.backend.domain.festival.dto.FestivalInfoResponseDto;
import inu.unithon.backend.domain.festival.dto.FestivalIntroResponseDto;
import inu.unithon.backend.domain.festival.dto.FestivalResponseDto;
import inu.unithon.backend.domain.translate.entity.FestivalContentTranslate;
import inu.unithon.backend.domain.translate.entity.FestivalTranslate;
import inu.unithon.backend.domain.translate.entity.TranslateLanguage;
import inu.unithon.backend.domain.festival.mapper.FestivalMapper;
import inu.unithon.backend.domain.translate.repository.sql.festivalContentTranslate.FestivalContentTranslateRepository;
import inu.unithon.backend.domain.translate.repository.sql.festivalTranslate.FestivalTranslateRepository;
import inu.unithon.backend.global.exception.CustomException;
import inu.unithon.backend.global.exception.FestivalErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static inu.unithon.backend.global.exception.FestivalErrorCode.FESTIVAL_DB_SEARCH_ERROR;

@Primary
@Slf4j
@Service("festivalServiceImplV2")
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FestivalServiceImplV2 implements FestivalService {

  private final FestivalMapper festivalMapper;
  private final FestivalTranslateRepository translateRepository;
  private final FestivalContentTranslateRepository contentTranslateRepository;


  @Override // festival entity
  public FestivalResponseDto getFestivalList(String lang, String numOfRows, String pageNo, String eventStartDate, String areaCode, String eventEndDate) {
    try {
      int page = Integer.parseInt(pageNo);
      int size = Integer.parseInt(numOfRows);
      Pageable pageable = PageRequest.of(page - 1, size);

      // 언어 필터로 페스티벌 목록 조회
      Page<FestivalTranslate> results = translateRepository.findByLanguage(
        TranslateLanguage.valueOf(lang), pageable
      );

      List<FestivalDto> items = results.getContent().stream()
        .map(festivalMapper::toDtoFromFestival)
        .toList();

      return buildFestivalResponse(items, page, size, (int) results.getTotalElements());
    } catch (Exception e) {
      log.error("Festival List DB Search error", e);
      throw new CustomException(FESTIVAL_DB_SEARCH_ERROR);
    }
  }

  @Override // content
  public FestivalResponseDto getFestivalInfo(String lang, String contentId) {
    try {
      List<FestivalContentTranslate> contents = contentTranslateRepository
        .findByContentIdAndLanguage(Long.parseLong(contentId), TranslateLanguage.valueOf(lang));

      List<FestivalDto> items = contents.stream()
        .map(festivalMapper::toDtoFromFestivalContent)
        .toList();

      return buildFestivalResponse(items, 1, items.size(), items.size());
    } catch (Exception e) {
      log.error("Festival Info DB error", e);
      throw new CustomException(FESTIVAL_DB_SEARCH_ERROR);
    }
  }

  @Override // festival entity
  public FestivalResponseDto getSearchFestival(String lang, String keyword, String numOfRows, String pageNo) {
    try {
      int page = Integer.parseInt(pageNo);
      int size = Integer.parseInt(numOfRows);
      Pageable pageable = PageRequest.of(page - 1, size);

      // title / address / content에서 keyword 검색
      Page<FestivalTranslate> results = translateRepository
        .findByKeyword(TranslateLanguage.valueOf(lang), keyword, pageable);

      List<FestivalDto> items = results.getContent().stream()
        .map(festivalMapper::toDtoFromFestival)
        .toList();

      return buildFestivalResponse(items, page, size, (int) results.getTotalElements());
    } catch (Exception e) {
      log.error("Festival DB Search error", e);
      throw new CustomException(FESTIVAL_DB_SEARCH_ERROR);
    }
  }

  @Override // content
  public FestivalIntroResponseDto getFestivalDetailIntro(String lang, String contentId, String contentTypeId) {
    try {
      FestivalContentTranslate content = contentTranslateRepository
        .findFirstByContentIdAndLanguage(Long.parseLong(contentId), TranslateLanguage.valueOf(lang))
        .orElseThrow(() -> new CustomException(FestivalErrorCode.FESTIVAL_NOT_FOUND));

      FestivalDto dto = festivalMapper.toDtoFromFestivalContent(content);
      return festivalMapper.toIntroFromFestivalContent(content);
    } catch (Exception e) {
      log.error("Festival Intro DB error", e);
      throw new CustomException(FestivalErrorCode.FESTIVAL_DB_SEARCH_ERROR);
    }
  }

  @Override // content
  public FestivalInfoResponseDto getFestivalDetailInfo(String lang, String contentId, String contentTypeId) {
    try {
      FestivalContentTranslate content = contentTranslateRepository
        .findFirstByContentIdAndLanguage(Long.parseLong(contentId), TranslateLanguage.valueOf(lang))
        .orElseThrow(() -> new CustomException(FestivalErrorCode.FESTIVAL_NOT_FOUND));

      FestivalDto dto = festivalMapper.toDtoFromFestivalContent(content);
      return festivalMapper.toInfoFromFestivalContent(content);
    } catch (Exception e) {
      log.error("Festival Info DB error", e);
      throw new CustomException(FestivalErrorCode.FESTIVAL_DB_SEARCH_ERROR);
    }
  }

  @Override // 버리기.
  public FestivalResponseDto getFestivalLocationFood(String lang, String MapX, String MapY, String NumOfRows, String PageNo, String Radius) {
    return FestivalResponseDto.builder()
      .response(null)
      .build(); // 현재는 API 형태 유지용 stub
  }

  private FestivalResponseDto buildFestivalResponse(List<FestivalDto> items, int page, int size, int total) throws IllegalAccessException {
    FestivalResponseDto.FestivalBody.Items itemsWrapper = new FestivalResponseDto.FestivalBody.Items();
    FieldUtils.writeField(itemsWrapper, "item", items, true);

    FestivalResponseDto.FestivalBody body = new FestivalResponseDto.FestivalBody();
    FieldUtils.writeField(body, "items", itemsWrapper, true);
    FieldUtils.writeField(body, "numOfRows", size, true);
    FieldUtils.writeField(body, "pageNo", page, true);
    FieldUtils.writeField(body, "totalCount", total, true);

    FestivalResponseDto.FestivalHeader header = new FestivalResponseDto.FestivalHeader();
    FieldUtils.writeField(header, "resultCode", "0000", true);
    FieldUtils.writeField(header, "resultMsg", "OK", true);

    FestivalResponseDto.FestivalResponse response = new FestivalResponseDto.FestivalResponse();
    FieldUtils.writeField(response, "header", header, true);
    FieldUtils.writeField(response, "body", body, true);

    return FestivalResponseDto.builder()
      .response(response)
      .build();
  }
}
