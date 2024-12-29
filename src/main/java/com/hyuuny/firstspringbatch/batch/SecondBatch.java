package com.hyuuny.firstspringbatch.batch;

import com.hyuuny.firstspringbatch.entity.WinEntity;
import com.hyuuny.firstspringbatch.repository.WinRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.batch.item.data.builder.RepositoryItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Collections;
import java.util.Map;

@RequiredArgsConstructor
@Configuration
public class SecondBatch {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager platformTransactionManager;
    private final WinRepository winRepository;

    @Bean
    public Job secondJob() {
        return new JobBuilder("secondJob", jobRepository)
                .start(secondStep())
                .build();
    }

    @Bean
    public Step secondStep() {
        return new StepBuilder("secondStep", jobRepository)
                .<WinEntity, WinEntity>chunk(10, platformTransactionManager)
                .reader(winReader())
                .processor(trueProcessor())
                .writer(winWriter())
                .build();
    }

    // win >= 10 조건에 해당하면 processor로 전달
    @Bean
    public RepositoryItemReader<WinEntity> winReader() {
        return new RepositoryItemReaderBuilder<WinEntity>()
                .name("winReader")
                .pageSize(10)
                .methodName("findByWinGreaterThanEqual")
                .arguments(Collections.singletonList(10L))
                .repository(winRepository)
                .sorts(Map.of("id", Sort.Direction.ASC))
                .build();
    }

    // reader에서 넘어온 모든 데이터에 reward = true
    @Bean
    public ItemProcessor<WinEntity, WinEntity> trueProcessor() {
        return item -> {
            item.setReward(true);
            return item;
        };
    }

    @Bean
    public RepositoryItemWriter<WinEntity> winWriter() {
        return new RepositoryItemWriterBuilder<WinEntity>()
                .repository(winRepository)
                .methodName("save")
                .build();
    }
}
