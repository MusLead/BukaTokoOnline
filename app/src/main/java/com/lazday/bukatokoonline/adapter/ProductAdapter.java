package com.lazday.bukatokoonline.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.request.RequestOptions;
import com.lazday.bukatokoonline.R;
import com.lazday.bukatokoonline.activity.DetailActivity;
import com.lazday.bukatokoonline.data.Model.Product;
import com.lazday.bukatokoonline.utils.Converter;


import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    private List<Product.Data> products;
    Context context;

    public ProductAdapter(Context context, List<Product.Data> data){
        this.context = context;
        this.products = data;
    }


    @NonNull
    @Override
    public ProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {


        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_main, null);


        return new ViewHolder(view);
    }

    @SuppressLint("CheckResult")
    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ViewHolder viewHolder, final int i) {

        viewHolder.txtName.setText(products.get(i).getProduct());

        int price = products.get(i).getPrice();

        String cur ="IDR " + Converter.rupiah(price);

        viewHolder.txtPrice.setText(cur);

        RequestOptions options =new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.lazdayblue)
                .error(R.drawable.no_file_foundl)
                .priority(Priority.HIGH);

        Glide.with(context)
                .load(products.get(i).getImage())
                .apply(options)
                .into(viewHolder.imgProduct);
//        Log.i("Glide make it", String.valueOf(i));

        viewHolder.imgProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("PRODUCT_ID",products.get(i).getId());
                intent.putExtra("PRODUCT_IMAGE",products.get(i).getImage());
                //Log.e("_stringImage :", intent.getStringExtra("PRODUCT_IMAGE"));
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgProduct;
        TextView txtPrice, txtName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgProduct =itemView.findViewById(R.id.imageProduct);

            txtName =itemView.findViewById(R.id.txtName);

            txtPrice =itemView.findViewById(R.id.txtPrice);

        }
    }
}

