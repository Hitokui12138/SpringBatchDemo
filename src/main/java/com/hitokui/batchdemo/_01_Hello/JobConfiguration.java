package com.hitokui.batchdemo._01_Hello;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;

import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Map;

@Configuration
@EnableBatchProcessing
public class JobConfiguration {
    //为了改JobParameters,手动加一个Launcher
    @Autowired
    private JobLauncher jobLauncher;

    @Bean
    /**
     * @Description: 创建任务对象
     * @Param: []
     * @Return: org.springframework.batch.core.Job
     */
    public Job helloWorldJob(JobRepository jobRepository, Step step1) {
        //job名字//开始step
        return new JobBuilder("helloWorldJob", jobRepository)
                .start(step1)
                .build();
    }

    @Bean
    public Step step1(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("step1", jobRepository)
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                        System.out.println("Hello World!");
                        return RepeatStatus.FINISHED;
                    }
                }, transactionManager)
                .build();
    }
}