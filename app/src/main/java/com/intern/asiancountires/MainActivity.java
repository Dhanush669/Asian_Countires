package com.intern.asiancountires;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    RecyclerView countryRecyclerView;
    List<CountryModelClass> modelClassList;
    CountryViewModel countryViewModel;
    SharedPreferences preferences;
    FloatingActionButton fab;
    CardView stateColor;
    TextView stateText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        countryRecyclerView=findViewById(R.id.countryRecyclerview);
        modelClassList=new ArrayList<>();

        fab=findViewById(R.id.fab);
        stateColor=findViewById(R.id.stateColor);
        stateText=findViewById(R.id.stateText);

        preferences=this.getSharedPreferences("Country Preference",MODE_PRIVATE);

        countryViewModel=new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.
                getInstance(this.getApplication())).get(CountryViewModel.class);


        ConnectivityManager conMgr =  (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();
        if (netInfo == null){
            offline();
        }else{
            online();
        }
    }

    public void offline(){
        stateText.setText("Offline");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            stateText.setTextColor(this.getColor(R.color.offline));
            stateColor.setCardBackgroundColor(this.getColor(R.color.offline));
        }

        if(getState()==null){
            CountryAdapter adapter=new CountryAdapter(MainActivity.this);
            countryViewModel.getOfflineData().observe(MainActivity.this, new Observer<List<CountryModelClass>>() {
                @Override
                public void onChanged(List<CountryModelClass> countryModelClasses) {
                    Log.i("offlineeee", String.valueOf(countryModelClasses.size()));
                    adapter.post(countryModelClasses);
                }
            });

            countryRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
            countryRecyclerView.setAdapter(adapter);
        }
        else {
            Toast.makeText(this, "You are Offline, No Data to Show!", Toast.LENGTH_SHORT).show();
        }
    }

    public void online(){
        stateText.setText("Online");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            stateText.setTextColor(this.getColor(R.color.online));
            stateColor.setCardBackgroundColor(this.getColor(R.color.online));
        }

        CountryAdapter adapter=new CountryAdapter(MainActivity.this);
        countryViewModel.getCountryList().observe(MainActivity.this, new Observer<List<CountryModelClass>>() {
            @Override
            public void onChanged(List<CountryModelClass> countryModelClasses) {
                adapter.post(countryModelClasses);
            }
        });

                countryRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                countryRecyclerView.setAdapter(adapter);
    }
    public String getState(){
        return preferences.getString("offline",null);
    }


    public void deleteAll(View view) {
        if(getState()==null) {
            countryViewModel.Delete();
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("offline", "no");
            editor.apply();
            Toast.makeText(this, "Data Deleted Successfully!", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "No Data to delete!", Toast.LENGTH_SHORT).show();
        }
    }
}