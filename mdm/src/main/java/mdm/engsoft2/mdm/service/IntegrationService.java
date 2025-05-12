package mdm.engsoft2.mdm.service;

import mdm.engsoft2.mdm.entity.CountryEntity;
import org.springframework.stereotype.Service;
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
        String url = "http://localhost:8080/countries";
        CountryEntity[] countriesArray = restTemplate.getForObject(url, CountryEntity[].class);
        return Arrays.stream(countriesArray).collect(Collectors.toList());
    }
}