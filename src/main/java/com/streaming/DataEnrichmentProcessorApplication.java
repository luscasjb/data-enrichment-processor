package com.streaming;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class DataEnrichmentProcessorApplication {

	public static void main(String[] args) {
		SpringApplication.run(DataEnrichmentProcessorApplication.class, args);
	}

}
