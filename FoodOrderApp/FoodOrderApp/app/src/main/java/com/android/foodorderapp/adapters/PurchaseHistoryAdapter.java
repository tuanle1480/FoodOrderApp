package com.android.foodorderapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.foodorderapp.R;
import com.android.foodorderapp.databinding.PurchaseHistoryRowBinding;
import com.android.foodorderapp.model.Menu;
import com.android.foodorderapp.model.PurchaseHistory;

import java.util.List;

public class PurchaseHistoryAdapter extends RecyclerView.Adapter<PurchaseHistoryAdapter.ViewHolder> {

    private List<PurchaseHistory> purchaseHistories;
    private PurchaseHistoryClickListener clickListener;

    public PurchaseHistoryAdapter(List<PurchaseHistory> purchaseHistories,PurchaseHistoryClickListener clickListener){
        this.purchaseHistories = purchaseHistories;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(PurchaseHistoryRowBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindData(purchaseHistories.get(holder.getAdapterPosition()));
    }

    @Override
    public int getItemCount() {
        return purchaseHistories.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        PurchaseHistoryRowBinding purchaseHistoryRowBinding;
        public ViewHolder(@NonNull PurchaseHistoryRowBinding purchaseHistoryRowBinding) {
            super(purchaseHistoryRowBinding.getRoot());
            this.purchaseHistoryRowBinding = purchaseHistoryRowBinding;

            purchaseHistoryRowBinding.purchaseHistoryLayout.setOnClickListener(v->{
                clickListener.viewHistory(getAdapterPosition());
            });
        }

        public void bindData(PurchaseHistory data){
            purchaseHistoryRowBinding.txtBuyDate.setText(data.getDate());
        }
    }

    public interface PurchaseHistoryClickListener{
        void viewHistory(int index);
    }
}
