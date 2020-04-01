package com.lzkill.sgq.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

@Configuration
@ConditionalOnProperty(prefix = "application.scheduler", name = "enabled", havingValue = "true", matchIfMissing = true)
@EnableScheduling
public class SchedulerConfiguration implements SchedulingConfigurer {

	private final Logger log = LoggerFactory.getLogger(SchedulerConfiguration.class);

	@Value("${application.scheduler.taskpoolsize}")
	private final int schedulerTaskPoolSize = 10;

	@Override
	public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
		log.debug("Setting scheduler pool size: {}", schedulerTaskPoolSize);

		ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();

		threadPoolTaskScheduler.setPoolSize(schedulerTaskPoolSize);
		threadPoolTaskScheduler.setThreadNamePrefix("scheduled-task-pool-");
		threadPoolTaskScheduler.initialize();

		scheduledTaskRegistrar.setTaskScheduler(threadPoolTaskScheduler);
	}
}
