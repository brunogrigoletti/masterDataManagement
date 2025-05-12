package mdm.engsoft2.mdm.controller;

import mdm.engsoft2.mdm.entity.CountryEntity;
import mdm.engsoft2.mdm.repository.CountryRepository;
import mdm.engsoft2.mdm.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/countries")
public class CountryController {

    private final CountryService service;
    private final CountryRepository repository;

    @Autowired
    public CountryController(CountryService service, CountryRepository repository) {
        this.service = service;
        this.repository = repository;
    }

    @PostMapping("/create")
    public CountryEntity create(@RequestBody CountryEntity country) {
        return service.create(country);
    }

    @GetMapping("/all")
    public List<CountryEntity> getAll() {
        return service.getAll();
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<CountryEntity> getByID(@PathVariable String id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<CountryEntity> getByName(@PathVariable String name) {
        return repository.findByCommonName(name)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/capital/{capital}")
    public ResponseEntity<CountryEntity> getByCapital(@PathVariable String capital) {
        return repository.findByCapital(capital)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/region/{region}")
    public List<CountryEntity> getByRegion(@PathVariable String region) {
        return repository.findAll()
                .stream()
                .filter(c -> c.getRegion() != null && c.getRegion().equalsIgnoreCase(region))
                .toList();
    }

    @PutMapping("/{id}")
    public CountryEntity update(@PathVariable String id, @RequestBody CountryEntity country) {
        return service.update(id, country);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        service.delete(id);
    }
}