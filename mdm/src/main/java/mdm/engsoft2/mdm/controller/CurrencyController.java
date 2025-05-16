package mdm.engsoft2.mdm.controller;

import mdm.engsoft2.mdm.entity.CurrencyEntity;
import mdm.engsoft2.mdm.repository.CurrencyRepository;
import mdm.engsoft2.mdm.service.CurrencyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/mdm/currencies")
public class CurrencyController {

    private final CurrencyRepository repository;
    private final CurrencyService service;

    public CurrencyController(CurrencyRepository repository, CurrencyService service) {
        this.repository = repository;
        this.service = service;
    }

    @GetMapping("/all")
    public List<CurrencyEntity> getAll() {
        return repository.findAll();
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<CurrencyEntity> getById(@PathVariable String id) {
        Optional<CurrencyEntity> currency = repository.findById(id);
        return currency.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/code/{code}")
    public Optional<CurrencyEntity> getByCode(@PathVariable String code) {
        return repository.findByCurrencyCode(code);
    }

    @PostMapping("/create")
    public boolean create(@RequestBody CurrencyEntity currency) {
        return service.create(currency);
    }

    @PatchMapping("/update/{id}")
    public boolean update(@PathVariable String id, @RequestBody Map<String, Object> updates) {
        return service.patch(id, updates);
    }

    @DeleteMapping("/delete/{id}")
    public boolean delete(@PathVariable String id) {
        return service.delete(id);
    }
}