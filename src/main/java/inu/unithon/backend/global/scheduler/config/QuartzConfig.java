package inu.unithon.backend.global.scheduler.config;

import inu.unithon.backend.global.scheduler.job.FestivalUpdateJob;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.quartz.*;
import org.quartz.impl.RemoteScheduler;
import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;
import org.quartz.impl.StdSchedulerFactory;
import inu.unithon.backend.global.scheduler.jobListner.MyJobListener;

import javax.sql.DataSource;
import java.util.TimeZone;

@Configuration
@RequiredArgsConstructor
public class QuartzConfig {

//    private Scheduler scheduler;
    private final DataSource dataSource;
    private final ApplicationContext applicationContext;

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(JobDetail festivalJobDetail,
                                                     Trigger festivalTrigger) {
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        factory.setDataSource(dataSource);
        factory.setOverwriteExistingJobs(true);
        factory.setWaitForJobsToCompleteOnShutdown(true);
        factory.setAutoStartup(true);
        factory.setStartupDelay(3);

        // ✅ 스프링 DI 되는 JobFactory 연결
        factory.setJobFactory(new AutowiringSpringBeanJobFactory(applicationContext));

        // ✅ 스케줄러가 알도록 명시 등록 (중요!)
        factory.setJobDetails(festivalJobDetail);
        factory.setTriggers(festivalTrigger);

        return factory;
    }

//    public QuartzConfig (Scheduler scheduler){
//        this.scheduler = scheduler;
//    }

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
//                .cronSchedule("0 0 22 ? * MON") //  월 2200
                .cronSchedule("0 45 16 ? * TUE") //  화 1500
                .inTimeZone(TimeZone.getTimeZone("Asia/Seoul"))
                .withMisfireHandlingInstructionDoNothing();

        return TriggerBuilder.newTrigger()
                .forJob(festivalJobDetail())
                .withIdentity("festivalUpdateTrigger", "default")
                .withSchedule(scheduleBuilder)
                .build();
    }


    //    @PostConstruct
//    private void jopProcess() throws SchedulerException {
//        cronScheduler();
//    }
//
//    private void cronScheduler() throws SchedulerException {
//        JobDetail festivalJob = festivalJobDetail();
//        Trigger festivalTrigger = festivalTrigger();
//
//        scheduler = new StdSchedulerFactory().getScheduler();
//        MyJobListener jobListner = new MyJobListener();
//        scheduler.getListenerManager().addJobListener(jobListner);
//        scheduler.start();
//        scheduler.scheduleJob(festivalJob , festivalTrigger);
//
//    }
}
