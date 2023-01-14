package com.android.foodorderapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.android.foodorderapp.adapters.PurchaseHistoryAdapter;
import com.android.foodorderapp.databinding.ActivityHistoryPurchaseBinding;
import com.android.foodorderapp.model.PurchaseHistory;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class HistoryPurchaseActivity extends AppCompatActivity implements PurchaseHistoryAdapter.PurchaseHistoryClickListener{
    private ActivityHistoryPurchaseBinding historyBinding;
    private List<PurchaseHistory> purchaseHistories;
    private PurchaseHistoryAdapter adapter;

    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        historyBinding = ActivityHistoryPurchaseBinding.inflate(getLayoutInflater());
        setContentView(historyBinding.getRoot());

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("History");
        actionBar.setDisplayHomeAsUpEnabled(true);

        reference.child("history").child(MainActivity.getUsername).get().addOnCompleteListener(snap ->{
            purchaseHistories = new ArrayList<>();
            snap.getResult().getChildren().forEach(cSnap ->{
                PurchaseHistory purchaseHistory = cSnap.getValue(PurchaseHistory.class);
                purchaseHistories.add(purchaseHistory);

            });
            adapter = new PurchaseHistoryAdapter(purchaseHistories,this);
            historyBinding.recyPuschaseHistory.setAdapter(adapter);
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        historyBinding = null;
    }

    @Override
    public void viewHistory(int index) {
        startActivity(new Intent(this,DetailsPurchaseHistoryActivity.class)
                .putExtra("orders",purchaseHistories.get(index)));
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home :
                finish();
            default:
                //do nothing
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(Activity.RESULT_CANCELED);
        finish();
    }
}