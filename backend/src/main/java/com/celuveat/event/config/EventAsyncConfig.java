package com.celuveat.event.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@EnableAsync
@Configuration
public class EventAsyncConfig {

    @Bean(name = "eventThreadPoolTaskExecutor")
    public ThreadPoolTaskExecutor eventThreadPoolTaskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(10);
        taskExecutor.setMaxPoolSize(10);
        taskExecutor.setQueueCapacity(1000);
        taskExecutor.setThreadNamePrefix("Event Executor-");
        taskExecutor.initialize();
        return taskExecutor;
    }
}
