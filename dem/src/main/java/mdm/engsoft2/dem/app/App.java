package mdm.engsoft2.dem.app;

import java.util.ArrayList;
import mdm.engsoft2.dem.transform.Country;
import mdm.engsoft2.dem.transform.TransformService;

public class App {

    public void printCountries() {
		ArrayList<Country> countries = new TransformService().getCountries();
		System.out.println("There are " + countries.size() + " countries.");
		for (Country country : countries) {
			System.out.println(country.getCommonName());
		}
	}
}