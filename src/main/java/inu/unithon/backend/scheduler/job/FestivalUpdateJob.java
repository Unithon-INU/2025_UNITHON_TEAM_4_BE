package inu.unithon.backend.scheduler.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

@Component
public class FestivalUpdateJob implements Job{

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        //TODO: 축제 정보 업데이트 로직 구현
        System.out.println("축제 정보 업데이트 작업이 실행되었습니다.");
    }

}
