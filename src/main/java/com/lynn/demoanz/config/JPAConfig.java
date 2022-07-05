package com.lynn.demoanz.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@Configuration
@EnableJpaAuditing(
        dateTimeProviderRef = "customDateTimeProvider"
)
@EnableJpaRepositories(basePackages = "com.lynn.demoanz.repository")
public class JPAConfig {
}
