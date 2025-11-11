package inu.unithon.backend.global.scheduler.config;

import inu.unithon.backend.global.scheduler.job.FestivalUpdateJob;
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
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule("0 30 15 ? * tue");// 매주 화요일 오후 3시 30분에 실행

        return TriggerBuilder.newTrigger()
                .forJob(festivalJobDetail())
                .withIdentity("FestivalUpdateTrigger (demo version)")
                .withSchedule(scheduleBuilder)
                .build();

    }
}
