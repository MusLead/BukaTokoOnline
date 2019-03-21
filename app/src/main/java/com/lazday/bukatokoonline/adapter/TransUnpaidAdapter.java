package com.lazday.bukatokoonline.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.lazday.bukatokoonline.R;
import com.lazday.bukatokoonline.data.Model.transaction.TransUser;

import java.util.List;

public class TransUnpaidAdapter extends RecyclerView.Adapter<TransUnpaidAdapter.ViewHolder> {

    private List<TransUser.Data> transUnpaid;
    Context context;

    public TransUnpaidAdapter(Context context, List<TransUser.Data> transaction) {
        this.context = context;
        this.transUnpaid = transaction;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {


        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_trans, null);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        TransUser.Data data= transUnpaid.get(i);
        viewHolder.txtTitle.setText(data.getTransaction_code());
        viewHolder.txtStatus.setText(data.getStatus_transaction());
    }


    @Override
    public int getItemCount() {
        return transUnpaid.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        Button btnAction;
        TextView txtTitle, txtStatus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            btnAction = itemView.findViewById(R.id.btnAction);

            txtTitle = itemView.findViewById(R.id.txtTitle);

            txtStatus = itemView.findViewById(R.id.txtStatus);

        }
    }
}
