package mdm.engsoft2.dem.metadata;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface MetadataRepository extends JpaRepository<MetadataEntity, String> {
    Optional<MetadataEntity> findByProvider(String provider);
    
    Optional<MetadataEntity> findByCount(Integer count);

    Optional<MetadataEntity> findByLastUpdate(String lastUpdate);
}