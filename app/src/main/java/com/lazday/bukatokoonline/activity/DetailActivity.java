package com.lazday.bukatokoonline.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.request.RequestOptions;
import com.glide.slider.library.Animations.DescriptionAnimation;
import com.glide.slider.library.SliderLayout;
import com.glide.slider.library.SliderTypes.BaseSliderView;
import com.glide.slider.library.SliderTypes.DefaultSliderView;
import com.glide.slider.library.Tricks.ViewPagerEx;
import com.lazday.bukatokoonline.App;
import com.lazday.bukatokoonline.Dialog.CartDialog;
import com.lazday.bukatokoonline.Dialog.LoginDialog;
import com.lazday.bukatokoonline.R;
import com.lazday.bukatokoonline.data.Constant;
import com.lazday.bukatokoonline.data.Model.Detail;
import com.lazday.bukatokoonline.data.Retrofit.Api;
import com.lazday.bukatokoonline.data.Retrofit.ApiInterface;
import com.lazday.bukatokoonline.utils.Converter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener{

    Bundle bundle;
    TextView txtName,txtPrice,txtDescription;
    SliderLayout sliderLayout;
    Button btnCheckOut;
    ImageButton btnAddCart;
    int detailPrice;
    String cartInt = "null";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        bundle = getIntent().getExtras();

        //Toast.makeText(this,bundle.getString("PRODUCT_IMAGE"),Toast.LENGTH_SHORT).show();
        //Log.e("_bundle :", bundle.getString("PRODUCT_IMAGE"));

        txtName = findViewById(R.id.txtName);
        txtDescription = findViewById((R.id.txtDescription));
        txtPrice = findViewById(R.id.txtPrice);

        getDetails();

        btnCheckOut= findViewById(R.id.btnCheckOut);
        btnCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(App.prefsManager.isLoggedIn()){
                    addToCart();
                    //ARTINYA LANGSUNG KE PAYMENT
                    Constant.SHOP_NOW = true;
                    //TODO:APA BEDANYA this sama activity.this
                    startActivity(new Intent(DetailActivity.this,CartActivity.class));
                    finish();
                }else{
                    new LoginDialog().showLoginDialog(DetailActivity.this);
                    Toast.makeText(getApplicationContext(),"Cart",Toast.LENGTH_SHORT).show();
                }

            }
        });



        btnAddCart = findViewById(R.id.btnAddCart);
        btnAddCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(App.prefsManager.isLoggedIn()){
                    addToCart();
                    new CartDialog().showCartDialog(DetailActivity.this);
                }else{
                    new LoginDialog().showLoginDialog(DetailActivity.this);
                    Toast.makeText(getApplicationContext(),"Cart",Toast.LENGTH_SHORT).show();
                }
            }
        });

        getSupportActionBar().setTitle("Detail Barang");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    private void addToCart(){

        if(App.sqLiteHelper.checkExist(bundle.getInt("PRODUCT_ID"))==0){

            Long cart_id = App.sqLiteHelper.addToCart(bundle.getInt("PRODUCT_ID"),txtName.getText()
                    .toString(),bundle.getString("PRODUCT_IMAGE"),detailPrice);

            Log.e("cart_id",String.valueOf(cart_id));
            cartInt = String.valueOf(cart_id);
        }else{
            Log.i("cart_id CHECKEXT",cartInt);
            Log.e("cart_id EXIST",String.valueOf(bundle.getInt("PRODUCT_ID")));
        }


    }

    private void getDetails(){
        ApiInterface apiInterface = Api.getUrl().create(ApiInterface.class);
        Call<Detail> call = apiInterface.getDetail(bundle.getInt("PRODUCT_ID"));
        call.enqueue(new Callback<Detail>() {
            @Override
            public void onResponse(Call<Detail> call, Response<Detail> response) {
                Detail.Data data = response.body().getData();
                txtName.setText(data.getProduct());
                txtPrice.setText("IDR " + Converter.rupiah(data.getPrice()));

                detailPrice = data.getPrice();

                if(data.getDescription() != null) {
                    txtDescription.setText(data.getDescription());
                }else{txtDescription.setText("No Description");}

                //TODO: TANYA KENAPA INI KOK DETAIL ATASNYA TETEP DETAIL>DATA
                //video 22 AS
                Detail detail = response.body();
                List<Detail.Data.Images> images = detail.getData().getImages();

                ArrayList<String> arrayList= new ArrayList<>();
                for(Detail.Data.Images img : images){
                    arrayList.add(img.getImage());
                    Log.e("_ada photo:",img.getImage());
                }

                setSlider(arrayList);

            }

            @Override
            public void onFailure(Call<Detail> call, Throwable t) {

            }
        });
    }

    private void setSlider(ArrayList<String> urlImage){
        sliderLayout = findViewById(R.id.slider);

        RequestOptions requestOptions= new RequestOptions();
        requestOptions.centerCrop();
        requestOptions.placeholder(R.drawable.lazdayblue);
        requestOptions.error(R.drawable.no_file_foundl);

        for (int i = 0; i < urlImage.size(); i++) {
            DefaultSliderView sliderView = new DefaultSliderView(this);
            // if you want show image only / without description text use DefaultSliderView instead

            // initialize SliderLayout
            sliderView
                    .image(urlImage.get(i))
                    .setRequestOption(requestOptions)
                    .setProgressBarVisible(false)
                    .setOnSliderClickListener(this);

            //add your extra information
            sliderView.bundle(new Bundle());
            sliderLayout.addSlider(sliderView);
        }

        // set Slider Transition Animation
        sliderLayout.setPresetTransformer(SliderLayout.Transformer.Default);
        //sliderLayout.setPresetTransformer(SliderLayout.Transformer.Accordion);

        sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        sliderLayout.setCustomAnimation(new DescriptionAnimation());

        sliderLayout.setDuration(4000);
        sliderLayout.addOnPageChangeListener(this);
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
