package mdm.engsoft2.dem;

import java.util.ArrayList;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemApplication {

	public static void main(String[] args) {
		//SpringApplication.run(DemApplication.class, args);
		printCountries();
	}

	private static void printCountries() {
		ArrayList<Country> countries = new ConsumerService().getCountries();
		System.out.println("There are " + countries.size() + " countries.");
		for (Country country : countries) {
			System.out.println(country.getCommonName());
		}
	}
}