package com.example.test.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @program: test1
 * @description:
 * @author: Zqm
 * @create: 2020-12-18 16:03
 **/
@Configuration
@EnableAsync
public class TaskExecutorConfig {

    @Bean
    public TaskExecutor cachGeo(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //设置核心数
        executor.setCorePoolSize(4);
        //设置最大线程数
        executor.setMaxPoolSize(16);
        //设置队列容量
        executor.setQueueCapacity(32);
        //设置线程活跃时间(秒)
        executor.setKeepAliveSeconds(0);
        //设置默认线程名称
        executor.setThreadNamePrefix("cachGeo-");
        //设置拒绝策略
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        //等待所有任务结束后再关闭线程池
        executor.setWaitForTasksToCompleteOnShutdown(true);
        return executor;
    }
}
