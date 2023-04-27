package com.example.anew;

import java.util.ArrayList;

public class CountryClient {

        private String status;
        private String message;
        private ArrayList<CountryModel> data;

    public CountryClient(String status, String message, ArrayList<CountryModel> countryModelArrayList) {
        this.status = status;
        this.message = message;
        this.data = countryModelArrayList;
    }

    public ArrayList<CountryModel> getCountryModelArrayList() {
        return data;
    }

    public String getStatus() {
        return status;
    }


    public String getMessage() {
        return message;
    }




}


