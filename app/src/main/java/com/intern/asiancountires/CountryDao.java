package com.intern.asiancountires;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CountryDao {
    @Insert
    void insert(CountryModelClass countryModelClass);

    @Query("SELECT * FROM countrymodelclass")
    LiveData<List<CountryModelClass>> getOfflineData();

    @Query("DELETE FROM countrymodelclass")
    void deleteAllCountry();
}
