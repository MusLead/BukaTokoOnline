package com.lazday.bukatokoonline.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.lazday.bukatokoonline.R;
import com.lazday.bukatokoonline.adapter.CityAdapter;
import com.lazday.bukatokoonline.data.Constant;
import com.lazday.bukatokoonline.data.Model.rajaongkir.City;
import com.lazday.bukatokoonline.data.Retrofit.Api;
import com.lazday.bukatokoonline.data.Retrofit.ApiInterface;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CityActivity extends AppCompatActivity {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.editText)
    EditText editText;

    CityAdapter adapter;

    @OnClick(R.id.imgCancel) void  clickCancel(){
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);

        ButterKnife.bind(this);

        RecyclerView.LayoutManager LayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(LayoutManager);

        getCity();

    }

    private void getCity(){
        ApiInterface apiInterface = Api.getUrlRajaOngkir(Constant.BASE_URL_RAJAONGKIR_STARTER).create(ApiInterface.class);
        Call<City> call = apiInterface.getCites(Constant.KEY_RAJAONGKIR);
        call.enqueue(new Callback<City>() {
            @Override
            public void onResponse(Call<City> call, Response<City> response) {
                City.RajaOngkir rajaOngkir = response.body().getRajaOngkir();
                List<City.RajaOngkir.Results> results = rajaOngkir.getResults();

                adapter= new CityAdapter(CityActivity.this,results);
                recyclerView.setAdapter(adapter);

                progressBar.setVisibility(View.GONE);

                getFilteredCity();
            }

            @Override
            public void onFailure(Call<City> call, Throwable t) {
                Log.e("_adapterFailure",String.valueOf(t));
            }
        });
    }

    private void getFilteredCity(){
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String search = editable.toString();

                adapter.getFilter().filter(search);
            }
        });
    }

}
