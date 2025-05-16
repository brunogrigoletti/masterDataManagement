package mdm.engsoft2.dem.metadata;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class MetadataEntity {
    @Id
    private String provider;
    private Integer count;
    private String lastUpdate;
}