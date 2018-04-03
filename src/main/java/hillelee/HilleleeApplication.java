package hillelee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableRetry
@EnableWebSecurity
public class HilleleeApplication {

	public static void main(String[] args) {
		SpringApplication.run(HilleleeApplication.class, args);
	}
}
