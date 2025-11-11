package inu.unithon.backend.global.scheduler.job;

import inu.unithon.backend.domain.festival.dto.FestivalDto;
import inu.unithon.backend.domain.festival.dto.FestivalResponseDto;
import inu.unithon.backend.domain.festival.service.FestivalSaveService;
import inu.unithon.backend.global.exception.CommonErrorCode;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.stereotype.Component;
import inu.unithon.backend.domain.festival.service.FestivalService;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import inu.unithon.backend.global.exception.CustomException;

@Slf4j
@Component
public class FestivalUpdateJob implements Job{

    private final FestivalService festivalService;
    private final FestivalSaveService festivalSaveService;

    public FestivalUpdateJob(FestivalService festivalService, FestivalSaveService festivalSaveService) {
        this.festivalService = festivalService;
      this.festivalSaveService = festivalSaveService;
    }

    @Override
    public void execute(JobExecutionContext context) {
        try{
            log.info("start@_@_@_@_@");
            String pageNum = "1";
            String numOfRows = "600";
            String startDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
            FestivalResponseDto response = festivalService.getFestivalList("kor", numOfRows, pageNum, startDate, null,null);
            List<FestivalDto> dtoList = response.getResponse().getBody().getItems().getItem();
            log.info("count  [nums={}]", response.getResponse().getBody().getTotalCount());
            festivalSaveService.saveFestivalList(dtoList);






        } catch (Exception e) {
            throw new CustomException(CommonErrorCode.LIST_UPDATE_FAILED);
        }
        System.out.println("축제 List update ");
    }

}

