package mdm.engsoft2.mdm.repository;

import mdm.engsoft2.mdm.entity.CurrencyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CurrencyRepository extends JpaRepository<CurrencyEntity, String> {
    Optional<CurrencyEntity> findByCurrencyCode(String currencyCode);

    Optional<CurrencyEntity> findByCurrencyName(String currencyName);

    Optional<CurrencyEntity> findByCurrencySymbol(String currencySymbol);

    Optional<CurrencyEntity> findByCountryId(String countryId);
}