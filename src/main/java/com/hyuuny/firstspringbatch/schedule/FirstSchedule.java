package com.hyuuny.firstspringbatch.schedule;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class FirstSchedule {

    private final JobLauncher jobLauncher;
    private final JobRegistry jobRegistry;

    // 매 분 10초에 실행
    @Scheduled(cron = "10 * * * * *", zone = "Asia/Seoul")
    public void rinFirstJob() throws Exception {
        log.info("first schedule start");

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss");
        String date = dateFormat.format(new Date());
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("date", date)
                .toJobParameters();
        jobLauncher.run(jobRegistry.getJob("firstJob"), jobParameters);
    }
}
