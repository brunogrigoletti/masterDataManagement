package mdm.engsoft2.mdm.repository;

import mdm.engsoft2.mdm.entity.CountryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CountryRepository extends JpaRepository<CountryEntity, String> {
    @SuppressWarnings("null")
    Optional<CountryEntity> findById(String id);

    Optional<CountryEntity> findByCommonName(String commonName);

    Optional<CountryEntity> findByCapital(String capital);
}