package com.intern.asiancountires;

import java.util.List;

public class CountryData {
    String name,capital,region,subregion,population,flag;
    List<CountryLanguageClass> languages;
    List<String> borders;

    public CountryData(String name, String capital, String region, String subregion, String population, String flag, List<CountryLanguageClass> languages, List<String> borders) {
        this.name = name;
        this.capital = capital;
        this.region = region;
        this.subregion = subregion;
        this.population = population;
        this.flag = flag;
        this.languages = languages;
        this.borders = borders;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getsubRegion() {
        return subregion;
    }

    public void setSubRegion(String subregion) {
        this.subregion = subregion;
    }

    public String getPopulation() {
        return population;
    }

    public void setPopulation(String population) {
        this.population = population;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public List<CountryLanguageClass> getLanguages() {
        return languages;
    }

    public void setLanguages(List<CountryLanguageClass> languages) {
        this.languages = languages;
    }

    public List<String> getBorders() {
        return borders;
    }

    public void setBorders(List<String> borders) {
        this.borders = borders;
    }
}
