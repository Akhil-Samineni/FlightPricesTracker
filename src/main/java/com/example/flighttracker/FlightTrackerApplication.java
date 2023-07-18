package com.example.flighttracker;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableBatchProcessing
@EnableScheduling
@EnableJpaRepositories
public class FlightTrackerApplication {

	public static void main(String[] args) {
		System.setProperty("webdriver.chrome.driver", "C:/Users/akhsam01/Git/FlightPricesTracker/src/main/resources/chromedriver.exe");
		SpringApplication.run(FlightTrackerApplication.class, args);
	}

}
