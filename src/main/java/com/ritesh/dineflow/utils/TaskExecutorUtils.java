package com.ritesh.dineflow.utils;

import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

public class TaskExecutorUtils {

	/**
	 * @param beanName      the name of bean needed to be created.
	 * @param corePoolSize  default size of the thread pool.
	 * @param maxPoolSize   max size of the thread pool.
	 * @param queueCapacity max number of values a thread can block.
	 * @return {@link TaskExecutor} with name {@literal beanName}
	 * @apiNote will be Used to create a thread pool executor.
	 */
	public static TaskExecutor createThreadPools(String beanName, int corePoolSize, int maxPoolSize,
			int queueCapacity) {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(corePoolSize);
		executor.setBeanName(beanName);
		executor.setMaxPoolSize(maxPoolSize);
		executor.setQueueCapacity(queueCapacity);
		executor.initialize();
		return executor;
	}
}
