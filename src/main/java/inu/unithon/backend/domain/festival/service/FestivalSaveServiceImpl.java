package inu.unithon.backend.domain.festival.service;

import inu.unithon.backend.domain.festival.dto.FestivalDto;
import inu.unithon.backend.domain.festival.entity.Festival;
import inu.unithon.backend.domain.festival.repository.festival.FestivalRepository;
import inu.unithon.backend.global.exception.CommonErrorCode;
import inu.unithon.backend.global.exception.CustomException;
import inu.unithon.backend.global.rabbitMq.RabbitMqProducer;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class FestivalSaveServiceImpl implements  FestivalSaveService{


  private static final Logger logger = LoggerFactory.getLogger(FestivalService.class);

  private static final DateTimeFormatter ymeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
  private final RabbitMqProducer rabbitMqProducer;

  private LocalDateTime parseYmdToDay(String ymd) {
    if (ymd == null || ymd.isBlank()) return null;
    LocalDate date = LocalDate.parse(ymd, ymeFormatter);
    return date.atStartOfDay();
  }


  private final FestivalRepository festivalRepository;

  @Transactional
  @Override
  public void saveFestivalList(List<FestivalDto> dtoList) {
    logger.info("@#@!#@!#!SAVING!@#@!@!#!@#");
    List<Long> newContentIds = dtoList.stream()
      .map(FestivalDto::getContentid)
      // 조회한 데이터 set에서 contentID만을 추출해서 stream을 통해 List 로 변환
      .toList();
    List<Long> oldContentIds = festivalRepository.findContentIdsByContentIds(newContentIds);
    // 변환한 List 를 통해 기존에 있는 data 인지 판별
    Set<Long> oldContentIdSet = new HashSet<>(oldContentIds);
    // 새로운 데이터 중 기존에 없는 데이터만을 모야 set으로

    List<Festival> festivalsToSave = dtoList.stream()
      .filter(dto -> !oldContentIdSet.contains(dto.getContentid()))
      // 앞서 만든 set을 통해 기존에 있는 data는 들어가지 않도록 설정
      .map(this::listToEntity)
      // Festival 형식으로 change 하여 필터에 안걸린것들로만
      .toList();
    if (!festivalsToSave.isEmpty()) {
      // 앞서 만든 festivalsToSave 리스트는 db에 현재 없는 새로운 데이터들로만 이루어진 Festival 객체들의 리스트 들로 이루어 졌으므로
      // 이 리스트가 비어있지 않다면 아래 로직을 실행
      festivalRepository.saveAll(festivalsToSave);
      logger.info(" 새로운 축제 정보 저장 완료: {}", festivalsToSave.size());
      festivalsToSave.forEach(f -> rabbitMqProducer.detailSend(String.valueOf(f.getContentId())));
      // 앞서 저장된 객체 리스트들을 각자 하나씩 rabbitMqProducer를 통해 detailSend 메소드를 등록
      logger.info(" RabbitMQ로 축제 상세 정보 전송 완료: {}", festivalsToSave.size());
    } else {
      logger.info(" nothing to new");
    }
  }

  private Festival listToEntity(FestivalDto dto) {
    try {
      LocalDateTime startDate = parseYmdToDay(dto.getEventstartdate());
      LocalDateTime endDate = parseYmdToDay(dto.getEventenddate());

      return Festival.builder()
        .contentId(dto.getContentid())
        .title(dto.getTitle())
        .startDate(startDate)
        .endDate(endDate)
        .imageUrl(dto.getFirstimage())
        .build();
    } catch (Exception e) {
      throw new CustomException(CommonErrorCode.DATE_PARSE_FAILED);
    }

  }
}
