package com.mchalet.dailyreport;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class DailyReportBackendApplication  {

	public static void main(String[] args) {
		SpringApplication.run(DailyReportBackendApplication.class, args);
	}

}
