package mdm.engsoft2.dem.metadata;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dem/metadata")
public class MetadataController {

    private final MetadataRepository repository;

    @Autowired
    public MetadataController(MetadataRepository repository) {
        this.repository = repository;
    }

    @GetMapping("")
    public ResponseEntity<Object> getMetadata() {
        List<MetadataEntity> metadata = repository.findAll();
        if (metadata.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("{\"error\":\"No metadata available.\"}");
        }
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(metadata);
    }

    @GetMapping("/{provider}")
    public ResponseEntity<MetadataEntity> getByProvider(@PathVariable String provider) {
        return repository.findByProvider(provider)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{count}")
    public ResponseEntity<MetadataEntity> getByCount(@PathVariable Integer count) {
        return repository.findByCount(count)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{lastUpdate}")
    public ResponseEntity<MetadataEntity> getByLastUpdate(@PathVariable String lastUpdate) {
        return repository.findByLastUpdate(lastUpdate)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}