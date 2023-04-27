package com.example.anew;

import java.util.ArrayList;

public class SelectedCountry {

    private static SelectedCountry instance;

    public static synchronized SelectedCountry getInstance() {

        if(instance==null){
            instance = new SelectedCountry();
        }

        return instance;
    }

    private ArrayList<CountryModel> countryModelsList = new ArrayList<>();

    public ArrayList<CountryModel> getCountryModelsList() {
        return countryModelsList;
    }

    public void setCountryModelsList(CountryModel countryModels) {
        countryModelsList.add(countryModels);
    }

    public SelectedCountry() {
    }


}
