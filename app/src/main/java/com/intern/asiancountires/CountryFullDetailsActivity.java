package com.intern.asiancountires;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou;

public class CountryFullDetailsActivity extends AppCompatActivity {

    ImageView vflag;
    TextView vcountry,vcapital,vregion,vsubregion,vlanguage,vpopulation,vborder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_full_details);

        Intent intent=getIntent();
        String country = intent.getStringExtra("country");
        String capital = intent.getStringExtra("capital");
        String flag = intent.getStringExtra("flag");
        String region = intent.getStringExtra("region");
        String subreion = intent.getStringExtra("subregion");
        String language = intent.getStringExtra("language");
        String population = intent.getStringExtra("population");
        String border = intent.getStringExtra("border");

        vcountry=findViewById(R.id.detail_coutry);
        vcapital=findViewById(R.id.detail_capital);
        vregion=findViewById(R.id.detail_region);
        vsubregion=findViewById(R.id.detail_subregion);
        vlanguage=findViewById(R.id.detail_language);
        vpopulation=findViewById(R.id.detail_population);
        vborder=findViewById(R.id.detail_border);
        vflag=findViewById(R.id.detail_flag);

        vcountry.setText(country);
        vcapital.setText(capital);
        vregion.setText(region);
        vsubregion.setText(subreion);
        vlanguage.setText(language);
        vpopulation.setText(population);
        vborder.setText(border);
        GlideToVectorYou.justLoadImage(this,
                Uri.parse(flag) , vflag);
    }
}