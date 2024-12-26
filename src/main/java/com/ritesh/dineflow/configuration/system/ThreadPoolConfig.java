package com.ritesh.dineflow.configuration.system;

import com.ritesh.dineflow.utils.TaskExecutorUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;

/**
 * <b>Will be used to create {@link TaskExecutor} for different use cases</b>.
 */
@Configuration
public class ThreadPoolConfig {

	/**
	 * <i>Will be used to execute sending email</i>.
	 *
	 * @return {@link TaskExecutor} with {@link Bean} name <b>emailTaskExecutor</b>.
	 */
	@Bean("emailTaskExecutor")
	public TaskExecutor emailTaskExecutor() {
		return TaskExecutorUtils.createThreadPools("EmailTaskExecutor", 10, 15, 100);
	}
}