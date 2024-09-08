package com.hitokui.batchdemo._01_Hello;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@EnableBatchProcessing
public class JobConfiguration {
    /**
     * @Description: 注入创建任务对象的对象
     */
    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    /**
     * @Description: //任务的执行由Step决定,注入创建Step对象的对象
     */
    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    //为了改JobParameters,手动加一个Launcher
    @Autowired
    private JobLauncher jobLauncher;

    @Bean
    /**
     * @Description: 创建任务对象
     * @Param: []
     * @Return: org.springframework.batch.core.Job
     */
    public Job helloWorldJob(Step step1) {
        //job名字//开始step
        return jobBuilderFactory.get("helloWorldJob")
                .start(step1)
                .build();
    }

    @Bean
    public Step step1() {
        /**
         * @Description: step1 step的名字，tasklet执行任务 可以用chunk
         * @Param: []
         * @Return: org.springframework.batch.core.Step
         */
        return stepBuilderFactory.get("step1")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
                        System.out.println("He11o World!");
                        //获得并修改JobParameter
                        Map<String, Object> jobParameters = chunkContext.getStepContext().getJobParameters();
                        System.out.println(jobParameters);

                        return RepeatStatus.FINISHED;
                    }

                }).build();
    }
}