package mdm.engsoft2.mdm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import mdm.engsoft2.mdm.entity.CountryEntity;

public interface CountryRepository extends JpaRepository<CountryEntity, String> {} 