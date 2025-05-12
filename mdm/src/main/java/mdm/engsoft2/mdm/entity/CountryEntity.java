package mdm.engsoft2.mdm.entity;

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
    private String officialName;
    private Boolean independent;
    private Boolean unMember;
    private String currencies;
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
}