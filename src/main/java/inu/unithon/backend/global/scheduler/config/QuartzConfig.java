package inu.unithon.backend.global.scheduler.config;

import inu.unithon.backend.global.scheduler.job.FestivalUpdateJob;
import lombok.RequiredArgsConstructor;
import org.quartz.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.sql.DataSource;
import java.util.TimeZone;

@Configuration
@RequiredArgsConstructor
public class QuartzConfig {

    private final DataSource dataSource;
    private final ApplicationContext applicationContext;

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() {
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        factory.setDataSource(dataSource);
        factory.setOverwriteExistingJobs(true);
        factory.setWaitForJobsToCompleteOnShutdown(true);

        // ✅ 스프링 빈 주입되는 JobFactory
        org.springframework.scheduling.quartz.SpringBeanJobFactory jobFactory =
                new org.springframework.scheduling.quartz.SpringBeanJobFactory();
        jobFactory.setApplicationContext(applicationContext);
        factory.setJobFactory(jobFactory);

        return factory;
    }

    @Bean
    public JobDetail festivalJobDetail() {
        return JobBuilder.newJob(FestivalUpdateJob.class)
                .withIdentity("festivalUpdateJob", "default")
                .storeDurably(true)
                .build();
    }

    @Bean
    public Trigger festivalTrigger() {
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder
                .cronSchedule("0 0 14 ? * WED")  // ✅ 대문자 WED 권장
                .inTimeZone(TimeZone.getTimeZone("Asia/Seoul")) // ✅ 타임존 고정
                .withMisfireHandlingInstructionDoNothing();

        return TriggerBuilder.newTrigger()
                .forJob(festivalJobDetail())
                .withIdentity("festivalUpdateTrigger", "default") // ✅ 공백/괄호 제거
                .withSchedule(scheduleBuilder)
                .build();
    }
}
