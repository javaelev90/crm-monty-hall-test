package se.comhem.test.montyhall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "se.comhem")
public class MontyHallApplication {

	public static void main(String[] args) {
		SpringApplication.run(MontyHallApplication.class, args);
	}
}
