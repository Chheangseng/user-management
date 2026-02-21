package com.tcs.user_auth_management.config.taskConfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.task.TaskExecutor;
import org.springframework.core.task.VirtualThreadTaskExecutor;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Configuration
@EnableAsync
public class TaskConfig {

    @Bean
    @Primary
    public TaskExecutor applicationTaskExecutor() {
        return new VirtualThreadTaskExecutor();
    }
    @Bean
    public TaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();

        // Configure the scheduler
        scheduler.setPoolSize(5);  // Number of threads in the pool
        scheduler.setThreadNamePrefix("scheduled-task-");  // Prefix for thread names
        scheduler.setDaemon(true);  // Allow JVM to exit when only daemon threads remain

        // Additional configuration (optional)
        scheduler.setAwaitTerminationSeconds(60);  // Wait for tasks to complete on shutdown
        scheduler.setWaitForTasksToCompleteOnShutdown(true);  // Complete tasks before shutdown

        return scheduler;
    }
}
