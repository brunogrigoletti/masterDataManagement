package mdm.engsoft2.mdm.service;

import mdm.engsoft2.mdm.entity.CurrencyEntity;
import mdm.engsoft2.mdm.repository.CurrencyRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class CurrencyService {
    private final CurrencyRepository currencyRepository;

    public CurrencyService(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    public List<CurrencyEntity> getAll() {
        return currencyRepository.findAll();
    }

    public boolean create(CurrencyEntity c) {
        List<CurrencyEntity> currencies = currencyRepository.findAll();
        for (int i = 0; i < currencies.size(); i++) {
            CurrencyEntity currency = currencies.get(i);
            if (currency.getCurrencyName().equals(c.getCurrencyName())) {
                return false;
            }
        }
        c.setId(UUID.randomUUID().toString());
        c.setCreatedAt(new java.sql.Timestamp(System.currentTimeMillis()));
        currencyRepository.save(c);
        return true;
    }

    public boolean patch(String id, Map<String, Object> updates) {
        Optional<CurrencyEntity> oc = currencyRepository.findById(id);
        if (oc.isEmpty()) {
            return false;
        }
        CurrencyEntity currency = oc.get();

        updates.forEach((key, value) -> {
            switch (key) {
                case "currencyCode":
                    currency.setCurrencyCode((String) value);
                    break;
                case "currencyName":
                    currency.setCurrencyName((String) value);
                    break;
                case "currencySymbol":
                    currency.setCurrencySymbol((String) value);
                    break;
                case "countryId":
                    currency.setCountryId((String) value);
                    break;
                case "createdAt":
                    currency.setCreatedAt(value != null ? java.sql.Timestamp.valueOf((String) value) : null);
                    break;
                case "updatedAt":
                    currency.setUpdatedAt(value != null ? java.sql.Timestamp.valueOf((String) value) : null);
                    break;
            }
        });

        currency.setUpdatedAt(new java.sql.Timestamp(System.currentTimeMillis()));
        currencyRepository.save(currency);
        return true;
    }

    public boolean delete(String id) {
        List<CurrencyEntity> currencies = currencyRepository.findAll();
        for (int i = 0; i < currencies.size(); i++) {
            CurrencyEntity currency = currencies.get(i);
            if (currency.getId().equals(id)) {
                currencyRepository.deleteById(id);
                return true;
            }
        }
        return false;
    }
}