package mdm.engsoft2.mdm.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;
import jakarta.annotation.PostConstruct;
import mdm.engsoft2.mdm.repository.CountryRepository;
import mdm.engsoft2.mdm.entity.CountryEntity;
import mdm.engsoft2.mdm.entity.CurrencyEntity;

@Service
public class CountryService {
    private final CountryRepository countryRepository;
    private final IntegrationService dem;

    public CountryService(CountryRepository countryRepository, IntegrationService dem) {
        this.countryRepository = countryRepository;
        this.dem = dem;
    }

    @PostConstruct
    private void syncWithDem() {
        System.out.println("Synchronizing countries from DEM service...");
        List<CountryEntity> countriesFromDem = dem.fetchCountriesFromDem();

        for (CountryEntity country : countriesFromDem) {
            if (country.getCurrencies() != null) {
                for (CurrencyEntity currency : country.getCurrencies()) {
                    currency.setId(UUID.randomUUID().toString());
                    currency.setCountryId(country.getId());
                }
            }
        }

        countryRepository.saveAll(countriesFromDem);
        System.out.println("Synchronization completed. " + countriesFromDem.size() + " countries saved.");
    }

    public List<CountryEntity> getAll() {
        return countryRepository.findAll();
    }

    public boolean create(CountryEntity c) {
        List<CountryEntity> countries = countryRepository.findAll();
        for (int i = 0; i < countries.size(); i++) {
            CountryEntity country = countries.get(i);
            if (country.getCommonName().equals(c.getCommonName())) {
                return false;
            }
        }
        c.setId(UUID.randomUUID().toString());
        c.setCreatedAt(new java.sql.Timestamp(System.currentTimeMillis()));

        if (c.getCurrencies() != null) {
            for (CurrencyEntity currency : c.getCurrencies()) {
                if (currency.getId() == null || currency.getId().isEmpty()) {
                    currency.setId(UUID.randomUUID().toString());
                }
                currency.setCountryId(c.getId());
            }
        }

        countryRepository.save(c);
        return true;
    }
    
    @SuppressWarnings("unchecked")
    public boolean patch(String id, Map<String, Object> updates) {
        Optional<CountryEntity> oc = countryRepository.findById(id);
        if (oc.isEmpty()) {
            return false;
        }
        CountryEntity country = oc.get();
    
        updates.forEach((key, value) -> {
            switch (key) {
                case "commonName": country.setCommonName((String) value); break;
                case "independent": country.setIndependent((Boolean) value); break;
                case "unMember": country.setUnMember((Boolean) value); break;
                case "currencies": country.setCurrencies((List<CurrencyEntity>) value); break;
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
        
        country.setUpdatedAt(new java.sql.Timestamp(System.currentTimeMillis()));

        if (country.getCurrencies() != null) {
            for (CurrencyEntity currency : country.getCurrencies()) {
                if (currency.getId() == null || currency.getId().isEmpty()) {
                    currency.setId(UUID.randomUUID().toString());
                }
                currency.setCountryId(country.getId());
            }
        }

        countryRepository.save(country);
        return true;
    }

    public boolean delete(String id) {
        List<CountryEntity> countries = countryRepository.findAll();
        for (int i = 0; i < countries.size(); i++) {
            CountryEntity country = countries.get(i);
            if (country.getId().equals(id)) {
                countryRepository.deleteById(id);
                return true;
            }
        }
        return false;
    }
}