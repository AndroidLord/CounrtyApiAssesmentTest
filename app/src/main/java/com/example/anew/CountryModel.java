package com.example.anew;

public class CountryModel {

    String countries_name,
            country_code,
            flag;

    public CountryModel(String countryName, String countryCode, String image) {
        this.countries_name = countryName;
        this.country_code = countryCode;
        this.flag = image;
    }

    public String getCountryName() {
        return countries_name;
    }


    public String getCountryCode() {
        return country_code;
    }

    public String getImage() {
        return flag;
    }
}
