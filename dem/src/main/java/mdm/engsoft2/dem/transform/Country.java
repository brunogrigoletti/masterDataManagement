package mdm.engsoft2.dem.transform;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Country {
    private String id;
    private String commonName;
    private Boolean independent;
    private Boolean unMember;
    private List<Currency> currencies;
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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Timestamp createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Timestamp updatedAt;

    public Country(String commonName, Boolean independent, Boolean unMember, List<Currency> currencies, String capital,
                   String region, String languages, String latlng, String borders, Double area, Long population,
                   String gini, String timezones, String continents) {
        this.id = UUID.randomUUID().toString();
        this.commonName = commonName;
        this.independent = independent;
        this.unMember = unMember;
        this.currencies = currencies;
        this.capital = capital;
        this.region = region;
        this.languages = languages;
        this.latlng = latlng;
        this.borders = borders;
        this.area = area;
        this.population = population;
        this.gini = gini;
        this.timezones = timezones;
        this.continents = continents;
        this.createdAt = new Timestamp(System.currentTimeMillis());
        this.updatedAt = null;
    }
}