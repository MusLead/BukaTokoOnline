package com.lazday.bukatokoonline.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lazday.bukatokoonline.App;
import com.lazday.bukatokoonline.R;
import com.lazday.bukatokoonline.adapter.CartAdapter;
import com.lazday.bukatokoonline.data.Constant;
import com.lazday.bukatokoonline.data.Model.rajaongkir.Cart;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {

    public static TextView txtPrceTotal;
    Button btnCheckOut;
    RecyclerView recyclerView;

    public static List<Cart> cartList = new ArrayList<>();

    public static CartAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        cartList.clear();
        cartList.addAll(App.sqLiteHelper.myCart());

        Log.i("cartList",String.valueOf(App.sqLiteHelper.myCart()));
        txtPrceTotal = findViewById(R.id.txtPriceTotal);
        btnCheckOut = findViewById(R.id.payNow);
        recyclerView = findViewById(R.id.recyclerViewCart);


        RecyclerView.LayoutManager LayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(LayoutManager);

        adapter = new CartAdapter(this, cartList);
        recyclerView.setAdapter(adapter);

        btnCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CartActivity.this, OngkirActivity.class));
            }
        });

        getSupportActionBar().setTitle("Cart");
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

        if(Constant.SHOP_NOW){
            startActivity(new Intent(this,OngkirActivity.class));
            Constant.SHOP_NOW =false;
        }
    }
}
