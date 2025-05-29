package mdm.engsoft2.mdm.service;

import mdm.engsoft2.mdm.entity.CountryEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class IntegrationService {

    private final RestTemplate restTemplate;

    public IntegrationService() {
        this.restTemplate = new RestTemplate();
    }

    public List<CountryEntity> fetchCountriesFromDem() {
        int maxAttempts = 10;
        int delayMs = 3000;
        for (int attempt = 1; attempt <= maxAttempts; attempt++) {
            try {
                String url = "http://dem:8080/dem/countries";
                CountryEntity[] countriesArray = restTemplate.getForObject(url, CountryEntity[].class);
                return Arrays.stream(countriesArray).collect(Collectors.toList());
            } catch (ResourceAccessException e) {
                System.err.println("Attempt " + attempt + " failed: " + e.getMessage());
                if (attempt == maxAttempts) {
                    System.err.println("Could not connect to DEM after " + maxAttempts + " attempts.");
                    return List.of();
                }
                try {
                    Thread.sleep(delayMs);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    return List.of();
                }
            }
        }
        return List.of();
    }
}