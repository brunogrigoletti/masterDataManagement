package mdm.engsoft2.dem.transform;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
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
            List<Map<String, Object>> rawList = objectMapper.readValue(data.getData(), new TypeReference<>() {});

            for (Map<String, Object> raw : rawList) {
                String commonName = null;
                if (raw.get("name") != null) {
                    commonName = (String) ((Map<String, Object>) raw.get("name")).get("common");
                }

                Boolean independent = raw.get("independent") != null ? (Boolean) raw.get("independent") : null;

                Boolean unMember = raw.get("unMember") != null ? (Boolean) raw.get("unMember") : null;

                // Consolida os valores do mapa "currencies" em uma única String
                String currencies = null;
                if (raw.get("currencies") != null) {
                    currencies = ((Map<String, Map<String, Object>>) raw.get("currencies"))
                            .entrySet()
                            .stream()
                            .map(entry -> entry.getKey() + ": " + entry.getValue().get("name"))
                            .collect(Collectors.joining(", "));
                }

                // Consolida os valores da lista "capital" em uma única String
                String capital = raw.get("capital") != null
                        ? String.join(", ", (List<String>) raw.get("capital"))
                        : null;

                String region = raw.get("region") != null ? (String) raw.get("region") : null;

                // Consolida os valores do mapa "languages" em uma única String
                String languages = null;
                if (raw.get("languages") != null) {
                    languages = ((Map<String, String>) raw.get("languages"))
                            .values()
                            .stream()
                            .collect(Collectors.joining(", "));
                }

                // Consolida os valores da lista "latlng" em uma única String
                String latlng = raw.get("latlng") != null
                        ? ((List<Double>) raw.get("latlng"))
                                .stream()
                                .map(String::valueOf)
                                .collect(Collectors.joining(", "))
                        : null;

                // Consolida os valores da lista "borders" em uma única String
                String borders = raw.get("borders") != null
                        ? String.join(", ", (List<String>) raw.get("borders"))
                        : null;

                Double area = raw.get("area") instanceof Number ? ((Number) raw.get("area")).doubleValue() : null;

                Long population = raw.get("population") instanceof Number ? ((Number) raw.get("population")).longValue() : null;

                // Consolida os valores do mapa "gini" em uma única String
                String gini = null;
                if (raw.get("gini") != null) {
                    gini = ((Map<String, Double>) raw.get("gini"))
                            .entrySet()
                            .stream()
                            .map(entry -> entry.getKey() + ": " + entry.getValue())
                            .collect(Collectors.joining(", "));
                }

                // Consolida os valores da lista "timezones" em uma única String
                String timezones = raw.get("timezones") != null
                        ? String.join(", ", (List<String>) raw.get("timezones"))
                        : null;

                // Consolida os valores da lista "continents" em uma única String
                String continents = raw.get("continents") != null
                        ? String.join(", ", (List<String>) raw.get("continents"))
                        : null;

                Country c = new Country(commonName, independent, unMember, currencies, capital, region, languages, latlng,
                        borders, area, population, gini, timezones, continents);

                countries.add(c);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return countries;
    }

    public ArrayList<Country> getCountries() {
        return createCountries();
    }
}