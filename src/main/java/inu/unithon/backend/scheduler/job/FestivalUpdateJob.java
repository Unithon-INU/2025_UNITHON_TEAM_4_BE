package inu.unithon.backend.scheduler.job;

import inu.unithon.backend.domain.festival.dto.FestivalResponseDto;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Component;
import inu.unithon.backend.domain.festival.service.FestivalService;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import inu.unithon.backend.domain.festival.dto.FestivalResponseDto;
import inu.unithon.backend.domain.festival.dto.FestivalDto;
import inu.unithon.backend.global.exception.CustomException;
import inu.unithon.backend.global.exception.ErrorCode;

@Component
public class FestivalUpdateJob implements Job{

    private final FestivalService festivalService;

    public FestivalUpdateJob(FestivalService festivalService) {
        this.festivalService = festivalService;
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        //TODO: 축제 정보 업데이트 로직 구현
        try{
            String pageNum = "1";
            String numOfRows = "600";
            String startDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
            FestivalResponseDto response = festivalService.getFestivalList("kor", numOfRows, pageNum, startDate, null,null);
            List<FestivalDto> dtoList = response.getResponse().getBody().getItems().getItem();
            festivalService.saveFestivalList(dtoList);
            //maiking db saving method response.toEntityList().forEach;




        } catch (Exception e) {
            throw new CustomException(ErrorCode.JOB_EXECUTION_FAILED);
        }
        System.out.println("축제 정보 업데이트 작업이 실행되었습니다.");
    }

}

