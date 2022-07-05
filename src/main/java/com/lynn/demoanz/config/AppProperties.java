package com.lynn.demoanz.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
@Configuration
@ConfigurationProperties("spring.app")
public class AppProperties {
	private RateLimit   rateLimit = new RateLimit();
	private List<Kafka> kafka     = new ArrayList<>();

	@Data
	public static class RateLimit {
		@Positive
		private int buffer = 300;

		@Positive
		private int sleep = 15;
	}


	@Data
	public static class Kafka {
		private int                 partitions;
		private int                 threads;
		private int                 pollTimeoutMs;
		@NotEmpty
		private List<String>        topics;
		private Map<String, String> properties;
	}
}
