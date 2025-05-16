package mdm.engsoft2.mdm.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;
import jakarta.annotation.PostConstruct;
import mdm.engsoft2.mdm.repository.CountryRepository;
import mdm.engsoft2.mdm.entity.CountryEntity;

@Service
public class CountryService {
    private final CountryRepository repository;
    private final IntegrationService dem;

    public CountryService(CountryRepository repository, IntegrationService dem) {
        this.repository = repository;
        this.dem = dem;
    }

    @PostConstruct
    private void syncWithDem() {
        System.out.println("Synchronizing countries from DEM service...");
        List<CountryEntity> countriesFromDem = dem.fetchCountriesFromDem();

        for (CountryEntity country : countriesFromDem) {
            if (country.getId() == null) {
                country.setId(UUID.randomUUID().toString());
            }
        }

        repository.saveAll(countriesFromDem);
        System.out.println("Synchronization completed. " + countriesFromDem.size() + " countries saved.");
    }

    public List<CountryEntity> getAll() {
        return repository.findAll();
    }

    public boolean create(CountryEntity c) {
        List<CountryEntity> countries = repository.findAll();
        for (int i = 0; i < countries.size(); i++) {
            CountryEntity country = countries.get(i);
            if (country.getCommonName().equals(c.getCommonName())) {
                return false;
            }
        }
        c.setId(UUID.randomUUID().toString());
        repository.save(c);
        return true;
    }
    
    public boolean patch(String id, Map<String, Object> updates) {
        Optional<CountryEntity> oc = repository.findById(id);
        if (oc.isEmpty()) {
            return false;
        }
        CountryEntity country = oc.get();
    
        updates.forEach((key, value) -> {
            switch (key) {
                case "commonName": country.setCommonName((String) value); break;
                case "independent": country.setIndependent((Boolean) value); break;
                case "unMember": country.setUnMember((Boolean) value); break;
                case "currencies": country.setCurrencies((String) value); break;
                case "capital": country.setCapital((String) value); break;
                case "region": country.setRegion((String) value); break;
                case "languages": country.setLanguages((String) value); break;
                case "latlng": country.setLatlng((String) value); break;
                case "borders": country.setBorders((String) value); break;
                case "area": country.setArea(value instanceof Number ? ((Number) value).doubleValue() : null); break;
                case "population": country.setPopulation(value instanceof Number ? ((Number) value).longValue() : null); break;
                case "gini": country.setGini((String) value); break;
                case "timezones": country.setTimezones((String) value); break;
                case "continents": country.setContinents((String) value); break;
            }
        });
    
        repository.save(country);
        return true;
    }

    public boolean delete(String id) {
        List<CountryEntity> countries = repository.findAll();
        for (int i = 0; i < countries.size(); i++) {
            CountryEntity country = countries.get(i);
            if (country.getId().equals(id)) {
                repository.deleteById(id);
                return true;
            }
        }
        return false;
    }
}