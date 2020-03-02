package com.lzkill.sgq.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties specific to Sgq.
 * <p>
 * Properties are configured in the {@code application.yml} file. See
 * {@link io.github.jhipster.config.JHipsterProperties} for a good example.
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {
	public Scheduler scheduler;
	public Cron cron;

	public Scheduler getScheduler() {
		return scheduler;
	}

	public void setScheduler(Scheduler scheduler) {
		this.scheduler = scheduler;
	}

	public Cron getCron() {
		return cron;
	}

	public void setCron(Cron cron) {
		this.cron = cron;
	}

	public static class Scheduler {
		public boolean enabled;
		public int taskPoolSize;

		public boolean isEnabled() {
			return enabled;
		}

		public void setEnabled(boolean enabled) {
			this.enabled = enabled;
		}

		public int getTaskPoolSize() {
			return taskPoolSize;
		}

		public void setTaskPoolSize(int taskPoolSize) {
			this.taskPoolSize = taskPoolSize;
		}

	}

	public static class Cron {

		public String syncSolicitacaoAnalise;

		public String getSyncSolicitacaoAnalise() {
			return syncSolicitacaoAnalise;
		}

		public void setSyncSolicitacaoAnalise(String syncSolicitacaoAnalise) {
			this.syncSolicitacaoAnalise = syncSolicitacaoAnalise;
		}
	}
}
