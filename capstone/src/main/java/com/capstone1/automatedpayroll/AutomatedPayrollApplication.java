package com.capstone1.automatedpayroll;

import com.capstone1.automatedpayroll.config.EnvConfig;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

import static org.springframework.boot.SpringApplication.*;

@SpringBootApplication
@EnableScheduling
@EntityScan(basePackages = {"com.capstone1.automatedpayroll.model"})
@ComponentScan(basePackages = {"com.capstone1.automatedpayroll"})
public class AutomatedPayrollApplication {

	public static void main(String[] args) {
		EnvConfig.load();
		run(AutomatedPayrollApplication.class, args);
	}

}
