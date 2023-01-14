package com.android.foodorderapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.foodorderapp.adapters.PlaceYourOrderAdapter;
import com.android.foodorderapp.databinding.ActivityDetailsPurchaseHistoryBinding;
import com.android.foodorderapp.model.Menu;
import com.android.foodorderapp.model.PurchaseHistory;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DetailsPurchaseHistoryActivity extends AppCompatActivity {
    private ActivityDetailsPurchaseHistoryBinding detailsBinding;
    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    private PurchaseHistory purchaseHistory;
    private PlaceYourOrderAdapter placeYourOrderAdapter;
    private List<Menu> items;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        detailsBinding = ActivityDetailsPurchaseHistoryBinding.inflate(getLayoutInflater());
        setContentView(detailsBinding.getRoot());

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Details");
        actionBar.setDisplayHomeAsUpEnabled(true);

        purchaseHistory = (PurchaseHistory) getIntent().getSerializableExtra("orders");
        if(purchaseHistory!=null){
            items = new ArrayList<>();
            for(Map.Entry<String,Menu> entry : purchaseHistory.getOrders().entrySet()){
                items.add(entry.getValue());
            }
            placeYourOrderAdapter = new PlaceYourOrderAdapter(items);
            detailsBinding.recyDetailsHistory.setAdapter(placeYourOrderAdapter);
        }else{
            Toast.makeText(this, "No history", Toast.LENGTH_SHORT).show();
        }
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        detailsBinding = null;
    }
}