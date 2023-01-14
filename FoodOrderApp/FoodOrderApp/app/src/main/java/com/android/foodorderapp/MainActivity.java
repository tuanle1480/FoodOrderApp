package com.android.foodorderapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.foodorderapp.adapters.RestaurantListAdapter;
import com.android.foodorderapp.model.Hours;
import com.android.foodorderapp.model.RestaurantModel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RestaurantListAdapter.RestaurantListClickListener {
    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    private List<RestaurantModel> restaurantData;
    Intent getIntentData;
    public static String getName, getAddress, getPhoneNumber,getUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Restaurant List");

        getIntentData = getIntent();
        getName = getIntentData.getStringExtra("name");
        getPhoneNumber = getIntentData.getStringExtra("phone");
        getAddress = getIntentData.getStringExtra("address");
        getUsername = getIntentData.getStringExtra("username");

        getRestaurantData();

    }

    private void initRecyclerView(List<RestaurantModel> restaurantModelList ) {
        RecyclerView recyclerView =  findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        RestaurantListAdapter adapter = new RestaurantListAdapter(restaurantModelList, this);
        recyclerView.setAdapter(adapter);
    }

    private void getRestaurantData() {
        reference.child("hours").get().addOnCompleteListener(Hsnap ->{
            Hours hours = Hsnap.getResult().getValue(Hours.class);
            reference.child("restaurant").get().addOnSuccessListener(snap ->{
                restaurantData = new ArrayList<>();
                snap.getChildren().forEach(cSnap ->{
                    RestaurantModel restaurant = cSnap.getValue(RestaurantModel.class);
                    restaurant.setHours(hours);
                    restaurantData.add(restaurant);
                });
                initRecyclerView(restaurantData);
            });
        });
    }


    @Override
    public void onItemClick(RestaurantModel restaurantModel) {
        Intent intent = new Intent(MainActivity.this, RestaurantMenuActivity.class);
        intent.putExtra("RestaurantModel", restaurantModel);
        startActivity(intent);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_cart:
                startActivity(new Intent(this,CartActivity.class).putExtra("request","home"));
                break;

            case R.id.menu_person:
                startActivity(new Intent(this,PersonalActivity.class));
                break;

            case R.id.menu_history_purchase:
                startActivity(new Intent(this,HistoryPurchaseActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}