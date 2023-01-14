package com.android.foodorderapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.foodorderapp.adapters.MenuListAdapter;
import com.android.foodorderapp.model.Menu;
import com.android.foodorderapp.model.RestaurantModel;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RestaurantMenuActivity extends AppCompatActivity implements MenuListAdapter.MenuListClickListener {
    private List<Menu> menuList = null;
    private MenuListAdapter menuListAdapter;
    private HashMap<String,Menu> itemsInCartList;
    private int totalItemInCart = 0;
    private Button buttonCheckout, btAddAllToCart;

    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_menu);

        ActionBar actionBar = getSupportActionBar();
        RestaurantModel restaurantModel = (RestaurantModel) getIntent().getSerializableExtra("RestaurantModel");
        if(restaurantModel != null) {
            actionBar.setTitle(restaurantModel.getName());
            actionBar.setSubtitle(restaurantModel.getAddress());
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        menuList = new ArrayList<>();
        for(Map.Entry<String,Menu> entry : restaurantModel.getMenus().entrySet()){
           menuList.add(entry.getValue());
        }

        initRecyclerView();


        buttonCheckout = findViewById(R.id.buttonCheckout);
        btAddAllToCart = findViewById(R.id.btAddToCart);

        buttonCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(itemsInCartList != null && itemsInCartList.size() <= 0) {
                    Toast.makeText(RestaurantMenuActivity.this, "Please add some items in cart.", Toast.LENGTH_SHORT).show();
                    return;
                }
                restaurantModel.setMenus(itemsInCartList);
                Intent i = new Intent(RestaurantMenuActivity.this, PlaceYourOrderActivity.class);
                i.putExtra("RestaurantModel", restaurantModel);
                startActivityForResult(i, 1000);
            }
        });

        btAddAllToCart.setOnClickListener(v->{
            if(itemsInCartList != null && itemsInCartList.size() <= 0) {
                Toast.makeText(RestaurantMenuActivity.this, "Please add some items in cart.", Toast.LENGTH_SHORT).show();
                return;
            }
            restaurantModel.setMenus(itemsInCartList);
            Intent i = new Intent(RestaurantMenuActivity.this, CartActivity.class);
            i.putExtra("RestaurantModel", restaurantModel);
            i.putExtra("request", "ordering");

            for(Map.Entry<String,Menu> entry : restaurantModel.getMenus().entrySet()){
                reference.child("cart").child(MainActivity.getUsername).child(entry.getValue().getId()).setValue(entry.getValue());
            }
            startActivityForResult(i,1000);
        });
    }

    private void initRecyclerView() {
        RecyclerView recyclerView =  findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        menuListAdapter = new MenuListAdapter(menuList, this);
        recyclerView.setAdapter(menuListAdapter);
    }



    @Override
    public void onAddToCartClick(Menu menu) {
        if(itemsInCartList == null) {
            itemsInCartList = new HashMap<>();
        }
        itemsInCartList.put(menu.getId(),menu);
        totalItemInCart = 0;

//        for(Menu m : itemsInCartList) {
//            totalItemInCart = totalItemInCart + m.getTotalInCart();
//        }
        for(Map.Entry<String,Menu> entry : itemsInCartList.entrySet()){
            totalItemInCart = totalItemInCart + entry.getValue().getTotalInCart();
        }

        buttonCheckout.setText("Checkout (" +totalItemInCart +") items");
    }

    @Override
    public void onUpdateCartClick(Menu menu) {
        if(itemsInCartList.containsValue(menu)) {
//            int index = itemsInCartList.indexOf(menu);
            itemsInCartList.remove(menu.getId());
            itemsInCartList.put(menu.getId(),menu);

            totalItemInCart = 0;

            for(Map.Entry<String,Menu> entry : itemsInCartList.entrySet()){
                totalItemInCart = totalItemInCart + entry.getValue().getTotalInCart();
            }
            buttonCheckout.setText("Checkout (" +totalItemInCart +") items");
        }
    }

    @Override
    public void onRemoveFromCartClick(Menu menu) {
        if(itemsInCartList.containsValue(menu)) {
            itemsInCartList.remove(menu);
            totalItemInCart = 0;

            for(Map.Entry<String,Menu> entry : itemsInCartList.entrySet()){
                totalItemInCart = totalItemInCart + entry.getValue().getTotalInCart();
            }
            buttonCheckout.setText("Checkout (" +totalItemInCart +") items");
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1000 && resultCode == Activity.RESULT_OK) {
            //
            finish();
        }
    }
}