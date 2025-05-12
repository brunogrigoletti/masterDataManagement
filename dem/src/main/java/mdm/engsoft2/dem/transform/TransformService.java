package mdm.engsoft2.dem.transform;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import mdm.engsoft2.dem.extract.ExtractService;

@Service
public class TransformService {

    private final ObjectMapper objectMapper;
    private final ExtractService data = new ExtractService();

    public TransformService() {
        this.objectMapper = new ObjectMapper();
    }

    private ArrayList<Country> createCountries() {
        ArrayList<Country> countries = new ArrayList<>();
        try {
            List<Map<String, Object>> rawList = objectMapper.readValue(data.getData(),new TypeReference<>(){});

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