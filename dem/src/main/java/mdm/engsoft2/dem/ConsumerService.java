package mdm.engsoft2.dem;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ConsumerService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public ConsumerService() {
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
    }

    private String consumeRestCountries() {
        String url = "https://restcountries.com/v3.1/all";
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        return response.getBody();
    }

    private ArrayList<Country> createCountries() {
        ArrayList<Country> countries = new ArrayList<>();
        try {
            List<Map<String, Object>> rawList = objectMapper.readValue(consumeRestCountries(),new TypeReference<>(){});

            for (Map<String, Object> raw : rawList) {
                @SuppressWarnings("unchecked")
                Country c = new Country(
                    (Map<String, Object>) raw.get("name"),
                    (Boolean) raw.get("independent"),
                    (Boolean) raw.get("unMember"),
                    (Map<String, Map<String, Object>>) raw.get("currencies"),
                    (List<String>) raw.get("capital"),
                    (String) raw.get("region"),
                    (Map<String, String>) raw.get("languages"),
                    (List<Double>) raw.get("latlng"),
                    (List<String>) raw.get("borders"),
                    raw.get("area") instanceof Number ? ((Number) raw.get("area")).doubleValue() : null,
                    raw.get("population") instanceof Number ? ((Number) raw.get("population")).longValue() : null,
                    (Map<String, Double>) raw.get("gini"),
                    (List<String>) raw.get("timezones"),
                    (List<String>) raw.get("continents"));

            countries.add(c);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return countries;
    }

    public ArrayList<Country> getCountries() {
        ArrayList<Country> safeList = createCountries();
        return safeList;
    }
}