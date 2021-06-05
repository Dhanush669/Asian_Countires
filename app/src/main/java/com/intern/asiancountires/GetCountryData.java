package com.intern.asiancountires;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GetCountryData {

    @GET("asia")
    Call<List<CountryData>> getCountryData();
}
