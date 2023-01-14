package com.android.foodorderapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.foodorderapp.adapters.PlaceYourOrderAdapter;
import com.android.foodorderapp.model.Menu;
import com.android.foodorderapp.model.PurchaseHistory;
import com.android.foodorderapp.model.RestaurantModel;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class PlaceYourOrderActivity extends AppCompatActivity {

    private EditText inputName, inputAddress, inputPhone ;
    private RecyclerView cartItemsRecyclerView;
    private TextView tvSubtotalAmount, tvDeliveryChargeAmount, tvDeliveryCharge, tvTotalAmount, buttonPlaceYourOrder;

    private boolean isDeliveryOn;
    private PlaceYourOrderAdapter placeYourOrderAdapter;
    private MaterialCheckBox cbMoney,cbMomo;

    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_your_order);

        RestaurantModel restaurantModel = (RestaurantModel) getIntent().getSerializableExtra("RestaurantModel");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Payment");
        actionBar.setDisplayHomeAsUpEnabled(true);

        init();
        setPersonData();

        buttonPlaceYourOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPlaceOrderButtonClick(restaurantModel);
            }
        });

        cbMoney.setOnClickListener(v->{
            if(cbMomo.isChecked()) cbMomo.setChecked(false);
        });

        cbMomo.setOnClickListener(v->{
            if(cbMoney.isChecked()) cbMoney.setChecked(false);
        });

        initRecyclerView(restaurantModel);
        calculateTotalAmount(restaurantModel);
    }

    private void init(){
        inputName = findViewById(R.id.inputName);
        inputAddress = findViewById(R.id.inputAddress);
        inputPhone = findViewById(R.id.inputPhone);
        tvSubtotalAmount = findViewById(R.id.tvSubtotalAmount);
        tvDeliveryChargeAmount = findViewById(R.id.tvDeliveryChargeAmount);
        tvDeliveryCharge = findViewById(R.id.tvDeliveryCharge);
        tvTotalAmount = findViewById(R.id.tvTotalAmount);
        buttonPlaceYourOrder = findViewById(R.id.buttonPlaceYourOrder);

        cbMomo = findViewById(R.id.cbPaymentByMomo);
        cbMoney = findViewById(R.id.cbPayMentByMoney);

        cartItemsRecyclerView = findViewById(R.id.cartItemsRecyclerView);
    }

    private void setPersonData(){
        inputName.setText(MainActivity.getName);
        inputAddress.setText(MainActivity.getAddress);
        inputPhone.setText(MainActivity.getPhoneNumber);
    }


    private void calculateTotalAmount(RestaurantModel restaurantModel) {
        float subTotalAmount = 0f;

//        for(Menu m : restaurantModel.getMenus()) {
//            subTotalAmount += m.getPrice() * m.getTotalInCart();
//        }

        for(Map.Entry<String,Menu> entry : restaurantModel.getMenus().entrySet()){
            subTotalAmount += entry.getValue().getPrice() * entry.getValue().getTotalInCart();
        }


        tvSubtotalAmount.setText("$"+String.format("%.2f", subTotalAmount));
        if(isDeliveryOn) {
            tvDeliveryChargeAmount.setText("$"+String.format("%.2f", restaurantModel.getDelivery_charge()));
            subTotalAmount += restaurantModel.getDelivery_charge();
        }
        tvTotalAmount.setText("$"+String.format("%.2f", subTotalAmount));
    }

    private void onPlaceOrderButtonClick(RestaurantModel restaurantModel) {
        if(TextUtils.isEmpty(inputName.getText().toString())) {
            inputName.setError("Please enter name ");
            return;
        } else if(isDeliveryOn && TextUtils.isEmpty(inputAddress.getText().toString())) {
            inputAddress.setError("Please enter address ");
            return;
        }else if(isDeliveryOn && TextUtils.isEmpty(inputPhone.getText().toString())) {
            inputPhone.setError("Please enter phone number ");
            return;
        }

        if(!cbMomo.isChecked() && !cbMoney.isChecked()){
            Toast.makeText(this, "Please choose a payment method", Toast.LENGTH_SHORT).show();
        }else if(cbMoney.isChecked()){
            //start success activity..
            Intent i = new Intent(PlaceYourOrderActivity.this, OrderSucceessActivity.class);
            i.putExtra("RestaurantModel", restaurantModel);
            startActivityForResult(i, 1000);
        }else if(cbMomo.isChecked()){
            startActivityForResult(new Intent(this,MomoPaymentActivity.class),1000);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Random random = new Random();
            PurchaseHistory purchaseHistory = new PurchaseHistory(LocalDate.now().toString(),restaurantModel.getMenus());
            reference.child("history").child(MainActivity.getUsername).child(purchaseHistory.getDate()+random.nextInt(100000000))
                    .setValue(purchaseHistory);
        }

    }

    private void initRecyclerView(RestaurantModel restaurantModel) {
        cartItemsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Menu> menus = new ArrayList<>();
        for(Map.Entry<String,Menu> entry : restaurantModel.getMenus().entrySet()){
            menus.add(entry.getValue());
        }
        placeYourOrderAdapter = new PlaceYourOrderAdapter(menus);
        cartItemsRecyclerView.setAdapter(placeYourOrderAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(requestCode == 1000) {
            setResult(Activity.RESULT_OK);
            finish();
        }
        super.onActivityResult(requestCode, resultCode, data);
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