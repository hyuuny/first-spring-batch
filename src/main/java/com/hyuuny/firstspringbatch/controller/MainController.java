package com.hyuuny.firstspringbatch.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class MainController {

    private final JobLauncher jobLauncher;
    private final JobRegistry jobRegistry;

    @GetMapping("/first")
    public String firstBatch(@RequestParam String value) throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("date", value)
                .toJobParameters();
        jobLauncher.run(jobRegistry.getJob("firstJob"), jobParameters);
        return "ok";
    }

    @GetMapping("/second")
    public String secondBatch(@RequestParam String value) throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("date", value)
                .toJobParameters();
        jobLauncher.run(jobRegistry.getJob("secondJob"), jobParameters);
        return "ok";
    }

    @GetMapping("/third")
    public String thirdBatch(@RequestParam String value) throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("data", value)
                .toJobParameters();
        jobLauncher.run(jobRegistry.getJob("thirdJob"), jobParameters);
        return "ok";
    }

    @GetMapping("/fourth")
    public String fourthBatch(@RequestParam String value) throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("data", value)
                .toJobParameters();
        jobLauncher.run(jobRegistry.getJob("fourthJob"), jobParameters);
        return "ok";
    }

}
