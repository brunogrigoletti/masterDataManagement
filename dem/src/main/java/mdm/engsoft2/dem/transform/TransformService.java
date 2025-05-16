package mdm.engsoft2.dem.transform;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import mdm.engsoft2.dem.extract.ExtractService;
import mdm.engsoft2.dem.metadata.MetadataEntity;
import mdm.engsoft2.dem.metadata.MetadataRepository;

@Service
public class TransformService {

    private final ObjectMapper objectMapper;
    private final MetadataRepository repository;
    private final ExtractService data = new ExtractService();

    public TransformService(MetadataRepository repository) {
        this.objectMapper = new ObjectMapper();
        this.repository = repository;
    }

    @SuppressWarnings("unchecked")
    private ArrayList<Country> createCountries() {
        ArrayList<Country> countries = new ArrayList<>();
        try {
            List<Map<String, Object>> rawList = objectMapper.readValue(data.getRawData(), new TypeReference<>() {
            });

            for (Map<String, Object> raw : rawList) {
                String countryId = java.util.UUID.randomUUID().toString();

                String commonName = null;
                if (raw.get("name") != null) {
                    commonName = (String) ((Map<String, Object>) raw.get("name")).get("common");
                }

                Boolean independent = raw.get("independent") != null ? (Boolean) raw.get("independent") : null;

                Boolean unMember = raw.get("unMember") != null ? (Boolean) raw.get("unMember") : null;

                List<Currency> currencies = new ArrayList<>();
                if (raw.get("currencies") != null) {
                    Map<String, Map<String, Object>> currencyMap = (Map<String, Map<String, Object>>) raw
                            .get("currencies");
                    for (Map.Entry<String, Map<String, Object>> entry : currencyMap.entrySet()) {
                        String code = entry.getKey();
                        Map<String, Object> details = entry.getValue();
                        String name = (String) details.get("name");
                        String symbol = details.get("symbol") != null ? details.get("symbol").toString() : null;

                        Currency currency = new Currency(code, name, symbol, countryId);
                        currencies.add(currency);
                    }
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

                Long population = raw.get("population") instanceof Number ? ((Number) raw.get("population")).longValue()
                        : null;

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

                Country c = new Country(commonName, independent, unMember, currencies, capital, region, languages,
                        latlng,
                        borders, area, population, gini, timezones, continents);

                c.setId(countryId);
                countries.add(c);
                
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return countries;
    }

    private ArrayList<Country> getCountries() {
        ArrayList<Country> countries = createCountries();
        if (countries.isEmpty()) {
            System.out.println("No countries found.");
            return null;
        }
        return countries;
    }

    @PostConstruct
    private void saveBronze() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
        String currentDate = LocalDateTime.now().format(formatter);

        String fileName = "bronze_" + currentDate + ".json";
        String bronzePath = System.getProperty("user.dir") + "/files/bronze";

        File bronzeDir = new File(bronzePath);
        if (!bronzeDir.exists()) {
            boolean created = bronzeDir.mkdirs();
            if (created) {
                System.out.println("Directory 'bronze' created at: " + bronzePath);
            } else {
                System.err.println("Failed to create directory 'bronze' at: " + bronzePath);
                return;
            }
        }

        String filePath = bronzePath + File.separator + fileName;

        try (FileWriter fileWriter = new FileWriter(filePath)) {
            objectMapper.writeValue(fileWriter, createCountries());
            System.out.println("Bronze data saved to " + filePath);
        } catch (IOException e) {
            System.err.println("Error saving bronze data: " + e.getMessage());
        }

        MetadataEntity metadata = new MetadataEntity();
        metadata.setProvider("RestCountries API");
        metadata.setCount(getCountries().size());
        metadata.setLastUpdate(currentDate);
        repository.save(metadata);
    }

    public String getBronzeData() {
        String bronzePath = System.getProperty("user.dir") + "/files/bronze";
        File bronzeFile = new File(bronzePath);

        File mostRecentFile = null;
        try {
            mostRecentFile = Files.list(bronzeFile.toPath())
                    .filter(Files::isRegularFile)
                    .max(Comparator.comparingLong(path -> path.toFile().lastModified()))
                    .map(Path::toFile)
                    .orElse(null);
        } catch (IOException e) {
            System.err.println("Error accessing directory 'bronze': " + e.getMessage());
            return null;
        }

        StringBuilder fileContent = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(mostRecentFile))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                fileContent.append(line);
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }

        return fileContent.toString();
    }
}