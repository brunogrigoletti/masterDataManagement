package mdm.engsoft2.mdm.service;

import java.util.List;
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

    public CountryEntity create(CountryEntity country) {
        validate(country);
        return repository.save(country);
    }

    public List<CountryEntity> getAll() {
        return repository.findAll();
    }

    public CountryEntity update(String name, CountryEntity countryUpdated){
        validate(countryUpdated);
        countryUpdated.setCommonName(name);
        return repository.save(countryUpdated);
    }

    public void delete(String name) {
        repository.deleteById(name);
    }

    private void validate(CountryEntity country){
        if (country.getCommonName() == null || country.getCommonName().isBlank()){
            throw new IllegalArgumentException("Common name é obrigatório");
        }
        
        if (country.getOfficialName() == null || country.getOfficialName().isBlank()){
            throw new IllegalArgumentException("Official name é obrigatório");
        }
        //aqui se quiserem, podemos validar o resto dos atributos
    }
}