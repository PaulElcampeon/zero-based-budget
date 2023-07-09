package com.paulo.budgeting.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "com.paulo.budgeting.repo")
@Configuration
public class JpaConfig {
}
