package com.example.test.config;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.*;

/**
 * @program: test1
 * @description:
 * @author: Zqm
 * @create: 2020-12-18 16:03
 **/
@Configuration
@EnableAsync
public class TaskExecutorConfig {


    /**
     * 异步存储登录时间线程池
     */
    public static ExecutorService logLoginTimeThreadPool;

    static {
        //核心池大小
        int corePoolSize = 16;
        //线程池能创建的线程的最大数目
        int maximumPoolSize = corePoolSize * 2;
        //程池的工作线程空闲后，保持存活的时间 1分钟
        long keepAliveTime = 1L;
        //用来储存等待执行任务的队列
        BlockingQueue<Runnable> workQueue = new LinkedBlockingDeque<>(corePoolSize * 2);
        //线程工厂 使用 guava 开源框架的 ThreadFactoryBuilder 给线程池的线程设置名字
        ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("logLoginTimeThreadPool-thread-%d").build();
        //当队列和线程池都满了时拒绝任务的策略 AbortPolicy 默认策略
        RejectedExecutionHandler handler = new ThreadPoolExecutor.AbortPolicy();

        logLoginTimeThreadPool = new ThreadPoolExecutor(corePoolSize, maximumPoolSize,
                keepAliveTime, TimeUnit.MILLISECONDS, workQueue, threadFactory, handler);
    }

    @Bean
    public TaskExecutor cacheGeo(){
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
        executor.setThreadNamePrefix("cacheGeo-");
        //设置拒绝策略
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        //等待所有任务结束后再关闭线程池
        executor.setWaitForTasksToCompleteOnShutdown(true);
        return executor;
    }
}
