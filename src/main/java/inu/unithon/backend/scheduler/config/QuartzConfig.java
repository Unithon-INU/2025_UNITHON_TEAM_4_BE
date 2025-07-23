package inu.unithon.backend.scheduler.config;

import inu.unithon.backend.scheduler.job.FestivalUpdateJob;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuartzConfig {

    @Bean
    public JobDetail festivalJobDetail() {
        return JobBuilder.newJob(FestivalUpdateJob.class)
                .withIdentity("festivalUpdateJob")
                .storeDurably(true)
                .build();

    }

    @Bean
    public Trigger festivalTrigger() {
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule("0 0 2 ? * Mon");

        return TriggerBuilder.newTrigger()
                .forJob(festivalJobDetail())
                .withIdentity("FestivalUpdateTrigger (demo version)")
                .withSchedule(scheduleBuilder)
                .build();

    }
}
