package inu.unithon.backend.global.scheduler.jobListner;

import org.quartz.JobListener;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class MyJobListener implements JobListener{
    @Override
    public String getName() {
        return "MyJobListener";
    }

    @Override
    public void jobToBeExecuted(JobExecutionContext context) {
        System.out.println("Job is about to be executed: " + context.getJobDetail().getKey());
    }

    @Override
    public void jobExecutionVetoed(JobExecutionContext context) {
        System.out.println("Job execution was vetoed: " + context.getJobDetail().getKey());
    }

    @Override
    public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
        System.out.println("Job was executed: " + context.getJobDetail().getKey());
        if (jobException != null) {
            System.out.println("Exception thrown by job: " + jobException.getMessage());
        }
    }
}
