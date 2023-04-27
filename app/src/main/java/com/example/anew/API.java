package com.example.anew;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface API {


    @GET("mylocalbusiness/getCountries")
    abstract Call<CountryClient> getCountryInformation();


}
