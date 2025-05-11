package TaskFlow_api.TaskFlow_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class TaskFlowApiApplication {


	public static void main(String[] args) {
		SpringApplication.run(TaskFlowApiApplication.class, args);
	}

}
