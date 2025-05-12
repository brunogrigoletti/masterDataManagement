package mdm.engsoft2.dem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class DemApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemApplication.class, args);
	}
}