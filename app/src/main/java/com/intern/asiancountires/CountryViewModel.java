package com.intern.asiancountires;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import java.util.List;

public class CountryViewModel extends AndroidViewModel {

    CountryRepository countryRepository;
    LiveData<List<CountryModelClass>> countryList;
    LiveData<List<CountryModelClass>> OfflineData;
    public CountryViewModel(@NonNull Application application) {
        super(application);
        countryRepository=new CountryRepository(application);
        countryList=countryRepository.getCountryList();
        OfflineData=countryRepository.offlineData();
    }

    public LiveData<List<CountryModelClass>> getCountryList(){
        return countryList;
    }

    LiveData<List<CountryModelClass>> getOfflineData(){
        return OfflineData;
    }

    public void Delete(){
        countryRepository.DeleteAllData();
    }

}
