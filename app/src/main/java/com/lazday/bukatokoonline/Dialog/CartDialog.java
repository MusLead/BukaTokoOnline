package com.lazday.bukatokoonline.Dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;

import com.lazday.bukatokoonline.R;
import com.lazday.bukatokoonline.activity.CartActivity;
import com.lazday.bukatokoonline.data.Constant;

public class CartDialog {



    public void showCartDialog(final Context context){

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.dialog_cart,null);
        builder.setView(view);
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();

        view.findViewById(R.id.btnCart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, CartActivity.class));
                ((Activity) context).finish();
                alertDialog.dismiss();

            }
        });

        view.findViewById(R.id.btnPay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Constant.SHOP_NOW = true;
                context.startActivity(new Intent(context, CartActivity.class));
                ((Activity) context).finish();
                alertDialog.dismiss();
            }
        });


    }
}
