package inu.unithon.backend.global.scheduler.job;

import inu.unithon.backend.domain.festival.dto.FestivalDto;
import inu.unithon.backend.domain.festival.dto.FestivalResponseDto;
import inu.unithon.backend.domain.festival.service.FestivalSaveService;
import inu.unithon.backend.global.exception.CommonErrorCode;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;
import inu.unithon.backend.domain.festival.service.FestivalServiceImpl;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import inu.unithon.backend.global.exception.CustomException;

@Slf4j
@Component
public class FestivalUpdateJob implements Job{

    private final FestivalServiceImpl festivalService;
    private final FestivalSaveService festivalSaveService;

    public FestivalUpdateJob(FestivalServiceImpl festivalService, FestivalSaveService festivalSaveService) {
        this.festivalService = festivalService;
      this.festivalSaveService = festivalSaveService;
    }

    @Override
    public void execute(JobExecutionContext context){
        try{
            log.info("start@_@_@_@_@");
            String pageNum = "1";
            String numOfRows = "100";
            String startDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
            log.info("[CALL] before getFestivalList()");
            FestivalResponseDto response = festivalService.getFestivalList("kor", numOfRows, pageNum, startDate, null,null);
            log.info("[CALL] after getFestivalList(): resNull? {}", response == null);
            log.info("[BEAN] festivalService bean = {}", festivalService.getClass().getName());
            List<FestivalDto> dtoList = response.getResponse().getBody().getItems().getItem();
            log.info("count  [nums={}]", response.getResponse().getBody().getTotalCount());
            if(response.getResponse().getBody().getTotalCount() == 0){
                log.info("No Festival Data Found");
                throw new CustomException(CommonErrorCode.LIST_UPDATE_FAILED);
            }
            festivalSaveService.saveFestivalList(dtoList);

        } catch (Exception e) {
            JobExecutionException error = new JobExecutionException(e);
            log.error("Festival List error: {}", e.getMessage(), e);
            error.setRefireImmediately(true);
            throw new CustomException(CommonErrorCode.LIST_UPDATE_FAILED);

        }
        System.out.println("축제 List update ");
    }

}

