package mdm.engsoft2.mdm.entity;

import java.sql.Timestamp;
import java.util.List;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class CountryEntity {
    @Id
    private String id;
    private String commonName;
    private Boolean independent;
    private Boolean unMember;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "countryId")
    private List<CurrencyEntity> currencies;
    private String capital;
    private String region;
    private String languages;
    private String latlng;
    private String borders;
    private Double area;
    private Long population;
    private String gini;
    private String timezones;
    private String continents;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}