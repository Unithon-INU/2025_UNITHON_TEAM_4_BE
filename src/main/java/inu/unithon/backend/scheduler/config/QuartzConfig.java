//package inu.unithon.backend.scheduler.config;
//
//import inu.unithon.backend.scheduler.job.FestivalUpdateJob;
//import lombok.RequiredArgsConstructor;
//import org.quartz.*;
//import org.quartz.spi.TriggerFiredBundle;
//import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.ApplicationContextAware;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.scheduling.quartz.SchedulerFactoryBean;
//import org.springframework.scheduling.quartz.SpringBeanJobFactory;
//
//import javax.sql.DataSource;
//
//@RequiredArgsConstructor
//@Configuration
//public class QuartzConfig {
//
//    private final DataSource dataSource;
//    private final ApplicationContext applicationContext;
//
//
//    @Bean
//    public SchedulerFactoryBean schedulerFactoryBean() {
//        SchedulerFactoryBean factory = new SchedulerFactoryBean();
//        factory.setDataSource(dataSource);
//        factory.setJobFactory(springBeanJobFactory());
//        factory.setOverwriteExistingJobs(true);
//        factory.setWaitForJobsToCompleteOnShutdown(true);
//        return factory;
//    }
//
//    @Bean
//    public SpringBeanJobFactory springBeanJobFactory() {
//        AutowiringSpringBeanJobFactory jobFactory = new AutowiringSpringBeanJobFactory();
//        jobFactory.setApplicationContext(applicationContext);
//        return jobFactory;
//    }
//
//    // Spring의 의존성 주입을 지원하는 JobFactory 구현
//    private static class AutowiringSpringBeanJobFactory extends SpringBeanJobFactory implements ApplicationContextAware {
//        private transient AutowireCapableBeanFactory beanFactory;
//
//        @Override
//        public void setApplicationContext(ApplicationContext applicationContext) {
//            this.beanFactory = applicationContext.getAutowireCapableBeanFactory();
//        }
//
//        @Override
//        protected Object createJobInstance(TriggerFiredBundle bundle) throws Exception {
//            Object job = super.createJobInstance(bundle);
//            beanFactory.autowireBean(job);  // 핵심: 의존성 주입 적용
//            return job;
//        }
//    }
//
//    @Bean
//    public Scheduler scheduler(SchedulerFactoryBean schedulerFactoryBean) throws SchedulerException {
//        return schedulerFactoryBean.getScheduler(); // ✅ Spring DI JobFactory가 설정된 Scheduler만 사용
//    }
//
//    @Bean
//    public JobDetail festivalJobDetail() {
//        return JobBuilder.newJob(FestivalUpdateJob.class)
//                .withIdentity("festivalUpdateJob")
//                .storeDurably(true)
//                .build();
//
//    }
//
//    @Bean
//    public Trigger festivalTrigger() {
//        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule("0 0 2 ? * Mon");
//
//        return TriggerBuilder.newTrigger()
//                .forJob(festivalJobDetail())
//                .withIdentity("FestivalUpdateTrigger (demo version)")
//                .withSchedule(scheduleBuilder)
//                .build();
//
//    }
//
//
//}
