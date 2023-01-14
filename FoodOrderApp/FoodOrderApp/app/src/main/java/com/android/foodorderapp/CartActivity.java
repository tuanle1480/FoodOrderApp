package com.android.foodorderapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.foodorderapp.adapters.PlaceYourOrderAdapter;
import com.android.foodorderapp.databinding.ActivityCartBinding;
import com.android.foodorderapp.model.Menu;
import com.android.foodorderapp.model.RestaurantModel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartActivity extends AppCompatActivity {
    private ActivityCartBinding cartBinding;

    private PlaceYourOrderAdapter placeYourOrderAdapter;
    private List<Menu> menus;
    private RestaurantModel restaurantModel;
    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cartBinding = ActivityCartBinding.inflate(getLayoutInflater());
        setContentView(cartBinding.getRoot());

        String getRequest = getIntent().getStringExtra("request");
        switch (getRequest){
            case "home":
                initRecyclerView();
                break;

            case "ordering":
                restaurantModel = (RestaurantModel) getIntent().getSerializableExtra("RestaurantModel");
                ActionBar actionBar = getSupportActionBar();
                actionBar.setTitle("Cart");
                actionBar.setDisplayHomeAsUpEnabled(true);
                initRecyclerView(restaurantModel);
                calculateTotalAmount(restaurantModel);
                break;
        }



        cartBinding.btPurchase.setOnClickListener(v->{
            switch (getRequest){
                case "home":
                    RestaurantModel cart = new RestaurantModel();
                    HashMap<String,Menu> cartData = new HashMap<>();
                    for(Menu menu : menus){
                        cartData.put(menu.getId(),menu);
                    }
                    cart.setMenus(cartData);
                    startActivityForResult(new Intent(this,PlaceYourOrderActivity.class).putExtra("RestaurantModel",cart),1000);
                    break;

                case "ordering":
                    startActivityForResult(new Intent(this,PlaceYourOrderActivity.class).putExtra("RestaurantModel",restaurantModel),1000);
                    break;
            }
        });
    }

    private void initRecyclerView(RestaurantModel restaurantModel) {
         menus = new ArrayList<>();
        for(Map.Entry<String,Menu> entry : restaurantModel.getMenus().entrySet()){
            menus.add(entry.getValue());
        }
        placeYourOrderAdapter = new PlaceYourOrderAdapter(menus);
        cartBinding.recyCart.setAdapter(placeYourOrderAdapter);
    }

    private void initRecyclerView() {

        reference.child("cart").child(MainActivity.getUsername).get().addOnCompleteListener(snap ->{
            menus = new ArrayList<>();
            snap.getResult().getChildren().forEach(cSnap ->{
                menus.add(cSnap.getValue(Menu.class));
            });
            calculateTotalAmount(menus);
            placeYourOrderAdapter = new PlaceYourOrderAdapter(menus);
            cartBinding.recyCart.setAdapter(placeYourOrderAdapter);
        });

    }

    private void calculateTotalAmount(RestaurantModel restaurantModel) {
        float subTotalAmount = 0f;
        for(Map.Entry<String,Menu> entry : restaurantModel.getMenus().entrySet()){
            subTotalAmount += entry.getValue().getPrice() * entry.getValue().getTotalInCart();
        }

        cartBinding.txtTotalMoneyInCart.setText("$"+String.format("%.2f", subTotalAmount));
    }

    private void calculateTotalAmount(List<Menu> data) {
        float subTotalAmount = 0f;
        for (Menu menu : data){
            subTotalAmount += menu.getPrice() * menu.getTotalInCart();
        }

        cartBinding.txtTotalMoneyInCart.setText("$"+String.format("%.2f", subTotalAmount));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cartBinding = null;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home :
                finish();
                break;

            case R.id.menu_cart_remove:
                menus.removeAll(menus);
                placeYourOrderAdapter.updateData(menus);
                calculateTotalAmount(menus);
                reference.child("cart").child(MainActivity.getUsername).removeValue();
                break;
            default:
                //do nothing
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.cart_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(Activity.RESULT_CANCELED);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1000) {
            //
            setResult(Activity.RESULT_OK);
            finish();
        }
    }
}