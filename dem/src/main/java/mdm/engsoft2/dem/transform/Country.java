package mdm.engsoft2.dem.transform; 

import java.util.List;
import java.util.Map;

public class Country {
    private Map<String, Object> name;
    private Boolean independent;
    private Boolean unMember;
    private Map<String, Map<String, Object>> currencies;
    private List<String> capital;
    private String region;
    private Map<String, String> languages;
    private List<Double> latlng;
    private List<String> borders;
    private Double area;
    private Long population;
    private Map<String, Double> gini;
    private List<String> timezones;
    private List<String> continents;

    public Country(Map<String, Object> name, Boolean independent, Boolean unMember,
            Map<String, Map<String, Object>> currencies, List<String> capital, String region,
            Map<String, String> languages, List<Double> latlng, List<String> borders, Double area, Long population,
            Map<String, Double> gini, List<String> timezones, List<String> continents) {
        this.name = name;
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
    }

    // Getter for common name
    public String getCommonName() {
        return name != null ? (String) name.get("common") : null;
    }

    // Setter for common name
    public void setCommonName(String commonName) {
        if (name != null) {
            name.put("common", commonName);
        }
    }

    // Getter for official name
    public String getOfficialName() {
        return name != null ? (String) name.get("official") : null;
    }

    // Setter for official name
    public void setOfficialName(String officialName) {
        if (name != null) {
            name.put("official", officialName);
        }
    }

    // Getter for native name
    @SuppressWarnings("unchecked")
    public Map<String, Map<String, String>> getNativeName() {
        return name != null ? (Map<String, Map<String, String>>) name.get("nativeName") : null;
    }

    // Setter for native name
    public void setNativeName(Map<String, Map<String, String>> nativeName) {
        if (name != null) {
            name.put("nativeName", nativeName);
        }
    }

    public Boolean getIndependent() {
        return independent;
    }

    public void setIndependent(Boolean independent) {
        this.independent = independent;
    }

    public Boolean getUnMember() {
        return unMember;
    }

    public void setUnMember(Boolean unMember) {
        this.unMember = unMember;
    }

    public Map<String, Map<String, Object>> getCurrencies() {
        return currencies;
    }

    public void setCurrencies(Map<String, Map<String, Object>> currencies) {
        this.currencies = currencies;
    }

    public List<String> getCapital() {
        return capital;
    }

    public void setCapital(List<String> capital) {
        this.capital = capital;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public Map<String, String> getLanguages() {
        return languages;
    }

    public void setLanguages(Map<String, String> languages) {
        this.languages = languages;
    }

    public List<Double> getLatlng() {
        return latlng;
    }

    public void setLatlng(List<Double> latlng) {
        this.latlng = latlng;
    }

    public List<String> getBorders() {
        return borders;
    }

    public void setBorders(List<String> borders) {
        this.borders = borders;
    }

    public Double getArea() {
        return area;
    }

    public void setArea(Double area) {
        this.area = area;
    }

    public Long getPopulation() {
        return population;
    }

    public void setPopulation(Long population) {
        this.population = population;
    }

    public Map<String, Double> getGini() {
        return gini;
    }

    public void setGini(Map<String, Double> gini) {
        this.gini = gini;
    }

    public List<String> getTimezones() {
        return timezones;
    }

    public void setTimezones(List<String> timezones) {
        this.timezones = timezones;
    }

    public List<String> getContinents() {
        return continents;
    }

    public void setContinents(List<String> continents) {
        this.continents = continents;
    }
} 