package com.szh.carmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

//@EnableJpaRepositories(basePackages={"com.szh.carmanager.dao","com.szh.carmanager.database"})
//@EntityScan(basePackages={"com.szh.carmanager.dao","com.szh.carmanager.database"})
@SpringBootApplication
@EnableScheduling
public class CarmanagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CarmanagerApplication.class, args);
	}
}
