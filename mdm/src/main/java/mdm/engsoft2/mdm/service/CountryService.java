package mdm.engsoft2.mdm.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import mdm.engsoft2.mdm.repository.CountryRepository;
import mdm.engsoft2.mdm.entity.CountryEntity;
import org.springframework.stereotype.Service;

@Service
public class CountryService {
    private final CountryRepository repository;
    private final ObjectMapper objectMapper;

    public CountryService(CountryRepository repository) {
        this.repository = repository;
        this.objectMapper = new ObjectMapper();
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
        countryUpdated.setCommomName(name);
        return repository.save(countryUpdated);
    }

    public void delete(String name) {
        repository.deleteById(name);
    }

    private void validate(CountryEntity country){
        if (country.getCommomName() == null || country.getCommomName().isBlank()){
            throw new IllegalArgumentException("Common name é obrigatório");
        }
        if (country.getOfficialName() == null || country.getOfficialName().isBlank()){
            throw new IllegalArgumentException("Official name é obrigatório");
        }
        //aqui se quiserem, podemos validar o resto dos atributos
    }
}
