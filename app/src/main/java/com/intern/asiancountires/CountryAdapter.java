package com.intern.asiancountires;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou;

import java.util.ArrayList;
import java.util.List;


public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.ViewHolder>{
    private List<CountryModelClass> countrylist =new ArrayList<>();
    Context context;
    public CountryAdapter(Context context) {
//        this.countrylist = countrylist;
        this.context = context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view= LayoutInflater.from(parent.getContext()).inflate(R.layout.country_layout,parent,false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        CountryModelClass modelClass=countrylist.get(position);
        holder.name.setText(modelClass.getName());
        holder.capital.setText(modelClass.getCapital());
        GlideToVectorYou.justLoadImage((Activity) context,
                Uri.parse(modelClass.getFlag()) , holder.flag);
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,CountryFullDetailsActivity.class);

                intent.putExtra("country",modelClass.getName());
                intent.putExtra("capital",modelClass.getCapital());
                intent.putExtra("region",modelClass.getRegion());
                intent.putExtra("subregion",modelClass.getSubRegion());
                intent.putExtra("flag",modelClass.getFlag());
                intent.putExtra("border",modelClass.getBorder());
                intent.putExtra("language",modelClass.getLanguage());
                intent.putExtra("population",modelClass.getPopulation());

                context.startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount() {
        return countrylist.size();
    }

    public void post(List<CountryModelClass> countrylist){
        this.countrylist=countrylist;
        notifyDataSetChanged();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView name,capital;
        ImageView flag;
        LinearLayout linearLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.countryName);
            capital=itemView.findViewById(R.id.countryCapital);
            flag=itemView.findViewById(R.id.flag);
            linearLayout=itemView.findViewById(R.id.linearLayout);
        }
    }

}
