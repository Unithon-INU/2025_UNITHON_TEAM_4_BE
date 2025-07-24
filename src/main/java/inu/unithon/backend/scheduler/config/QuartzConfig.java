package inu.unithon.backend.scheduler.config;

import inu.unithon.backend.scheduler.job.FestivalUpdateJob;
import lombok.RequiredArgsConstructor;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.sql.DataSource;

@RequiredArgsConstructor
@Configuration
public class QuartzConfig {

    private final DataSource dataSource;

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() {
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        factory.setDataSource(dataSource);
        factory.setOverwriteExistingJobs(true);
        factory.setWaitForJobsToCompleteOnShutdown(true);
        return factory;
    }

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
