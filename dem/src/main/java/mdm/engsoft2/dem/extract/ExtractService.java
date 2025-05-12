package mdm.engsoft2.dem.extract;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;

@Service
public class ExtractService {

    private final RestTemplate restTemplate;

    public ExtractService() {
        this.restTemplate = new RestTemplate();
    }

    private String consumeRestCountries() {
        String url = "https://restcountries.com/v3.1/all";
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        return response.getBody();
    }

    public String getData() {
        String countries = consumeRestCountries();
        return countries;
    }
}