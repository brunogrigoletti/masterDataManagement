package mdm.engsoft2.dem.extract;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import jakarta.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
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

    @PostConstruct
    private void saveRaw() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
        String currentDate = LocalDateTime.now().format(formatter);

        String fileName = "raw_" + currentDate + ".json";
        String rawPath = System.getProperty("user.dir") + "/files/raw";

        File rawDir = new File(rawPath);
        if (!rawDir.exists()) {
            boolean created = rawDir.mkdirs();
            if (created) {
                System.out.println("Directory 'raw' created at: " + rawPath);
            } else {
                System.err.println("Failed to create directory 'raw' at: " + rawPath);
                return;
            }
        }

        String filePath = rawPath + File.separator + fileName;

        String rawData = consumeRestCountries();
        try (FileWriter fileWriter = new FileWriter(filePath)) {
            fileWriter.write(rawData);
            System.out.println("Raw data saved to " + filePath);
        } catch (IOException e) {
            System.err.println("Error saving raw data: " + e.getMessage());
        }
    }

    public String getRawData() {
        String rawPath = System.getProperty("user.dir") + "/files/raw";
        File rawFile = new File(rawPath);

        File mostRecentFile = null;
        try {
            mostRecentFile = Files.list(rawFile.toPath())
                    .filter(Files::isRegularFile)
                    .max(Comparator.comparingLong(path -> path.toFile().lastModified()))
                    .map(Path::toFile)
                    .orElse(null);
        } catch (IOException e) {
            System.err.println("Error accessing directory 'raw': " + e.getMessage());
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