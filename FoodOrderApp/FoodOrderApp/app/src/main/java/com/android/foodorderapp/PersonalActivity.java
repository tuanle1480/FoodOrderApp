package com.android.foodorderapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;

import com.android.foodorderapp.databinding.ActivityPersonalBinding;

public class PersonalActivity extends AppCompatActivity {
    private ActivityPersonalBinding personalBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        personalBinding = ActivityPersonalBinding.inflate(getLayoutInflater());
        setContentView(personalBinding.getRoot());
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Personal Information");
        actionBar.setDisplayHomeAsUpEnabled(true);

        personalBinding.txtNamePersonal.setText(MainActivity.getName);
        personalBinding.txtAddressPersonal.setText(MainActivity.getAddress);
        personalBinding.txtPhoneNumberPersonal.setText(MainActivity.getPhoneNumber);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        personalBinding = null;
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