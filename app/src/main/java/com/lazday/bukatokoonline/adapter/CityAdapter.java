package com.lazday.bukatokoonline.adapter;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.lazday.bukatokoonline.R;
import com.lazday.bukatokoonline.data.Constant;
import com.lazday.bukatokoonline.data.Model.rajaongkir.City;

import java.util.ArrayList;
import java.util.List;

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.ViewHolder> implements Filterable {

    private List<City.RajaOngkir.Results> results;
    private List<City.RajaOngkir.Results> resultsFiltered;
    private Context context;

    public CityAdapter(Context context, List<City.RajaOngkir.Results> data){
        this.results = data;
        this.resultsFiltered = data;
        this.context = context;
    }


    @NonNull
    @Override
    public CityAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        @SuppressLint("InflateParams")
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_city, null);

        return new CityAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        final City.RajaOngkir.Results city = resultsFiltered.get(i);

        viewHolder.txtCity.setText(city.getCity_name());

        viewHolder.txtCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Constant.DESTINATION = city.getCity_id();
                Constant.DESTINATION_NAME = city.getCity_name();

                ((Activity) context).finish();
            }
        });
    }


    @Override
    public int getItemCount() {
        return resultsFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                 String charString = charSequence.toString();
                 if(charString.isEmpty()){
                     resultsFiltered = results;
                 }else{
                     List<City.RajaOngkir.Results> resultsList = new ArrayList<>();
                     for(City.RajaOngkir.Results row :results){
                         if(row.getCity_name().toLowerCase().contains(charString.toLowerCase())){
                             resultsList.add(row);
                         }
                     }

                     resultsFiltered = resultsList;
                 }

                 FilterResults filterResults = new FilterResults();
                 filterResults.values = resultsFiltered;
                 return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                resultsFiltered = (ArrayList<City.RajaOngkir.Results>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    class ViewHolder extends RecyclerView.ViewHolder {


        TextView txtCity;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtCity =itemView.findViewById(R.id.txtCity);

        }
    }
}
