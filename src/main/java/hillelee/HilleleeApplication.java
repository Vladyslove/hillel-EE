package hillelee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@EnableRetry
public class HilleleeApplication {

	public static void main(String[] args) {
		SpringApplication.run(HilleleeApplication.class, args);
	}
}
