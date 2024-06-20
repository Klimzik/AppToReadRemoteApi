package org.example;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Country {
    public String region;
    public int population;
    public List<String> capital;

    @Override
    public String toString() {
        return "Country{" +
                "region='" + this.region + '\'' +
                ", population=" + this.population +
                ", capital=" + this.capital +
                '}';
    }
}