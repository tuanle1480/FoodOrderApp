package com.android.foodorderapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.android.foodorderapp.model.RestaurantModel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class OrderSucceessActivity extends AppCompatActivity {
    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_succeess);


        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Payment result");
        actionBar.setDisplayHomeAsUpEnabled(false);

        reference.child("cart").child(MainActivity.getUsername).removeValue();

        TextView buttonDone = findViewById(R.id.buttonDone);
        buttonDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(OrderSucceessActivity.this,MainActivity.class));
                setResult(RESULT_OK);
                finish();
            }
        });
    }
}