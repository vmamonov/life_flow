package dev.mamonov.lifeflow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("dev.mamonov.lifeflow")
public class LifeFlowApplication {

	public static void main(String[] args) {
		SpringApplication.run(LifeFlowApplication.class, args);
	}

}
