package com.lazday.bukatokoonline.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.lazday.bukatokoonline.App;
import com.lazday.bukatokoonline.R;
import com.lazday.bukatokoonline.data.Constant;
import com.lazday.bukatokoonline.data.Model.Cost;
import com.lazday.bukatokoonline.data.Model.transaction.TransPost;
import com.lazday.bukatokoonline.data.Retrofit.Api;
import com.lazday.bukatokoonline.data.Retrofit.ApiInterface;
import com.lazday.bukatokoonline.data.database.PrefsManager;
import com.lazday.bukatokoonline.utils.Converter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OngkirActivity extends AppCompatActivity {

    @BindView(R.id.linearSave)
    LinearLayout linearLayout;

    @BindView(R.id.linearTrans)
    LinearLayout linearTrans;

    @BindView(R.id.edtDestination)
    EditText edtDestination;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @OnClick(R.id.btnSave) void clickSave(){

        if(edtDestination.length() >0 || edtAddress.length() >0) {
            linearLayout.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);

            ArrayList<TransPost.Detail> arrayList = new ArrayList<>();
            for(int i=0;i<CartActivity.cartList.size();i++){
                //TODO:BAGAIMANA KONSEP GETTER STTER ITU ?
                TransPost.Detail detail = new TransPost().new Detail();
                detail.setProduct_id(CartActivity.cartList.get(i).getProduct_id());
                detail.setQty(CartActivity.cartList.get(i).getQty());
                detail.setPrice(CartActivity.cartList.get(i).getPrice());
                detail.setTotal(CartActivity.cartList.get(i).getTotal());

                arrayList.add(detail);
            }

            TransPost transPost = new TransPost();
            transPost.setUser_id(Integer.parseInt(App.prefsManager.getUSerDetails().get(PrefsManager.SESS_ID)));
            transPost.setDestination(edtDestination.getText().toString() + " - " + edtAddress.getText().toString());
            transPost.setOngkir(ongkirValue);
            transPost.setGrandtotal(CartActivity.adapter.getTotal() + ongkirValue);
            transPost.setDetailList(arrayList);

            postTransaction(transPost);

        }else{
            Toast.makeText(getApplicationContext(),"please fill the address of the reciever",Toast.LENGTH_LONG).show();
        }
    }

    @OnClick(R.id.edtDestination) void clickDestination(){
        startActivity(new Intent(OngkirActivity.this,CityActivity.class));
    }

    @BindView(R.id.txtOngkir)
    TextView txtOngkir;

    @BindView(R.id.spnService)
    Spinner spnService;

    @BindView(R.id.edtAddress)
    EditText edtAddress;

    @OnClick(R.id.btnTrans)void clickTransfer(){
        startActivity(new Intent(OngkirActivity.this,TransActivity.class));
    }
    @OnClick(R.id.txtDismiss)void clickBackToCart(){
        finish();
    }

    @OnClick(R.id.txtCancel)void clickCancel(){
        finish();
    }

    List<String> listService;
    List<Integer> listValue;



    private int ongkirValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ongkir);

        ButterKnife.bind(this);

        listService = new ArrayList<>();
        listValue = new ArrayList<>();

        getSupportActionBar().setTitle("Payment");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(!Constant.DESTINATION_NAME.equals("")){
            edtDestination.setText(Constant.DESTINATION_NAME);
            getServices();
        }
    }

    private void getServices(){
        listValue.clear();
        listService.clear();
        ApiInterface apiInterface = Api.getUrlRajaOngkir(Constant.BASE_URL_RAJAONGKIR_STARTER).create(ApiInterface.class);
        Call<Cost> call = apiInterface.getCost(Constant.KEY_RAJAONGKIR,"444",Constant.DESTINATION,
                "1000","jne");
        call.enqueue(new Callback<Cost>() {
            @Override
            public void onResponse(Call<Cost> call, Response<Cost> response) {
                Cost.RajaOngkir ongkir = response.body().getRajaOngkir();

                List<Cost.RajaOngkir.Results> results = ongkir.getResults();
                //TODO: KOK BISA KURIRNYA JNE HANYA YANG EKONOMIS< PADAHAL BANYAK OPSINYA
                for(int i = 0; i < results.size();i++){
                    Log.e("_lzdyServiceCode",results.get(i).getCode());

                    List<Cost.RajaOngkir.Results.Costs> costs = results.get(i).getCosts();
                    for(int j=0;j<costs.size();j++){
                        Log.e("_lzdyServiceCode",costs.get(i).getService());

                        List<Cost.RajaOngkir.Results.Costs.Data> data = costs.get(j).getCost();
                        for (int k=0;k<data.size();k++){
                            Log.e("_lzdyServiceCode",String.valueOf(data.get(k).getValue()));

                            listService.add("IDR " + Converter.rupiah(data.get(k).getValue()) +
                                    " JNE(" + costs.get(j).getService() + ")");

                            listValue.add(data.get(k).getValue());
                        }
                    }

                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(OngkirActivity.this,
                            android.R.layout.simple_list_item_1,listService);
                    spnService.setAdapter(arrayAdapter);

                    spnService.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            String textOngkir = "IDR " + Converter.rupiah(listValue.get(i));
                            txtOngkir.setText(textOngkir);
                            ongkirValue = listValue.get(i);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });

                }
            }

            @Override
            public void onFailure(Call<Cost> call, Throwable t) {

            }
        });

    }

    private void postTransaction(TransPost transPost){
        ApiInterface apiInterface = Api.getUrl().create(ApiInterface.class);
        Call<TransPost> call = apiInterface.insertTrans(transPost);

        call.enqueue(new Callback<TransPost>() {
            @Override
            public void onResponse(Call<TransPost> call, Response<TransPost> response) {
                Log.e("_inside :",String.valueOf(response));

                if(response.isSuccessful()){
                    linearTrans.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);

                    Toast.makeText(getApplicationContext(),"Transaction has been made",
                            Toast.LENGTH_LONG).show();

                    App.sqLiteHelper.clearTable();
                }
            }

            @Override
            public void onFailure(Call<TransPost> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Log.e("_errorPostTrans",String.valueOf(t));
                Toast.makeText(getApplicationContext(),"Error found :" + String.valueOf(t),
                        Toast.LENGTH_LONG).show();
            }
        });
    }
}
