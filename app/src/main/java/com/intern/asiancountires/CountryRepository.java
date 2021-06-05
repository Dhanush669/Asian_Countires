package com.intern.asiancountires;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CountryRepository {
    Retrofit retrofit;
    GetCountryData request;
    Call<List<CountryData>> call1;
    List<CountryModelClass> modelClassList;
    LiveData<List<CountryModelClass>> OfflineData;
    Context context;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    ExecutorService executorService;
    CountryDao dao;
    CountryRepository(Application application){
        retrofit = new Retrofit.Builder()
                .baseUrl("https://restcountries.eu/rest/v2/region/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        request = retrofit.create(GetCountryData.class);
        call1=request.getCountryData();
        context=application.getApplicationContext();
        preferences=context.getSharedPreferences("Country Preference",Context.MODE_PRIVATE);
        editor=preferences.edit();
        executorService= Executors.newFixedThreadPool(1);
        CountryRoom room=CountryRoom.getInstance(application);
        dao=room.countryDao();
        OfflineData=dao.getOfflineData();
    }

    LiveData<List<CountryModelClass>> getCountryList(){
        modelClassList=new ArrayList<>();
        MutableLiveData<List<CountryModelClass>> data = new MutableLiveData<>();
        call1.enqueue(new Callback<List<CountryData>>() {
            @Override
            public void onResponse(Call<List<CountryData>> call, Response<List<CountryData>> response) {
                List<CountryData> countryData= (List<CountryData>) response.body();
                for(CountryData cd:countryData){
                    String language="";
                    String border="";
                    border+=getBorder(cd.getBorders());
                    language+=getLanguage(cd.getLanguages());

                    CountryModelClass modelClass=new CountryModelClass(cd.getName(),cd.getCapital(),cd.getRegion(),
                            cd.getsubRegion(),cd.getPopulation(),language,border,cd.getFlag());
                    modelClassList.add(modelClass);
                    data.setValue(modelClassList);

                    if(preferences.getString("firstTime",null)==null){
                        //Insert
                        Insert(modelClass);
                    }

                }
                editor.putString("firstTime","no");
                editor.apply();

            }

            @Override
            public void onFailure(Call<List<CountryData>> call, Throwable t) {
                Log.i("Exceptionnnnn",t.getMessage());
            }
        });


        return data;
    }

    public String getBorder(List<String> borders){
        String border="";
        for(int i=0;i<borders.size();i++){
            if(i!=borders.size()-1)
                border+=borders.get(i)+", ";
            else
                border+=borders.get(i);
        }

        return border;
    }

    public String getLanguage(List<CountryLanguageClass> languages){
        String language="";
        for(int i=0;i<languages.size();i++){
            CountryLanguageClass cd=languages.get(i);
            if(i!=languages.size()-1)
                language+=cd.getName()+", ";
            else
                language+=cd.getName();
        }
        return language;
    }


    public void Insert(CountryModelClass countryModelClass){
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                dao.insert(countryModelClass);

            }
        });
    }

    LiveData<List<CountryModelClass>> offlineData(){
        return OfflineData;
    }

    public void DeleteAllData(){
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                dao.deleteAllCountry();
            }
        });
    }
}
