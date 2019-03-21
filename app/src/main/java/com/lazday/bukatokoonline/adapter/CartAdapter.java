package com.lazday.bukatokoonline.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.request.RequestOptions;
import com.lazday.bukatokoonline.App;
import com.lazday.bukatokoonline.R;
import com.lazday.bukatokoonline.activity.CartActivity;
import com.lazday.bukatokoonline.data.Model.rajaongkir.Cart;
import com.lazday.bukatokoonline.utils.Converter;

import java.util.ArrayList;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    private Context context;
    private List<Cart> carts;
    TextView txtPriceTotal;

    public CartAdapter(Context context, List<Cart> carts){
        this.context = context;
        this.carts = carts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_cart,viewGroup,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {

        final Cart cart = carts.get(i);

        //TODO:KNP KOK Qty cmn 1 ?
        cart.setQty(1);
        cart.setTotal(cart.getTotal());

        viewHolder.txtName.setText(cart.getProduct());
        viewHolder.txtPrice.setText(Converter.rupiah(cart.getPrice()));

        RequestOptions options =new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.lazdayblue)
                .error(R.drawable.no_file_foundl)
                .priority(Priority.HIGH);

        Glide.with(context)
                .load(cart.getImage())
                .apply(options)
                .into(viewHolder.imgProduct);

        viewHolder.spinnerQty.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                int total = Integer.valueOf(adapterView.getItemAtPosition(i).toString()) *
                        cart.getPrice();
                String textTotal = "IDR " + Converter.rupiah(total);
                viewHolder.txtTotal.setText(textTotal);
                cart.setQty(Integer.valueOf(adapterView.getItemAtPosition(i).toString()));
                cart.setTotal(total);

                getTotal();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        viewHolder.btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setCancelable(false);

                builder.setTitle("Confirmation");
                builder.setMessage("Do you want to erase " + cart.getProduct() + " ?");

                builder.setPositiveButton("Erase", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        App.sqLiteHelper.removeItem(String.valueOf(cart.getCart_id()));
                        carts.remove(i);
                        notifyDataSetChanged();
                        Toast.makeText(context, "The Product has been Erased", Toast.LENGTH_LONG).show();
                        dialogInterface.dismiss();
                    }
                });

                builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        Toast.makeText(context, "canceled", Toast.LENGTH_LONG).show();
                        dialogInterface.dismiss();
                    }
                });

                builder.show();


            }
        });

    }

    @Override
    public int getItemCount() {
        return carts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgProduct;
        TextView txtName,txtPrice, txtTotal;
        Spinner spinnerQty;
        ImageButton btnDel;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.imgProd);
            txtPrice = itemView.findViewById(R.id.txtPrice);
            txtName = itemView.findViewById(R.id.txtName);
            btnDel = itemView.findViewById(R.id.btnDel);

            spinnerQty = itemView.findViewById(R.id.spnQty);
            txtTotal = itemView.findViewById(R.id.txtTotal);

            txtPriceTotal = itemView.findViewById(R.id.txtPriceTotal);

            ArrayList<String> totals = new ArrayList<>();

            for(int i =1;i<=100;i++){
                totals.add(String.valueOf(i));
            }

            ArrayAdapter<String> spinnerQtyAdapter = new ArrayAdapter<>(context,android.R.layout.simple_spinner_item,totals);
            spinnerQty.setAdapter(spinnerQtyAdapter);

        }
    }

    public int getTotal(){
        int priceTotal = 0;

        for(int i = 0;i<carts.size();i++){
            priceTotal += carts.get(i).getTotal();
        }

        String txtPriceTotals = "TOTAL : IDR " + Converter.rupiah(priceTotal);
        CartActivity.txtPrceTotal.setText(txtPriceTotals);
        return priceTotal;
    }
}
