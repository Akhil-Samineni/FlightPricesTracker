package com.example.flighttracker.controller;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledJobBean {
    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    Job batchJob;

    @Scheduled(cron = "*/10 * * * * *")
    //above is for every sec and below is for every minute
//    @Scheduled(cron = "0 */1 * * * *")
    public void perform() throws Exception
    {
        JobParameters params = new JobParametersBuilder()
                .addString("JobID", String.valueOf(System.currentTimeMillis()))
                .toJobParameters();
        jobLauncher.run(batchJob, params);
    }
}
