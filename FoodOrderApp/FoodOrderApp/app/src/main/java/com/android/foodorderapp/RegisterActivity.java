package com.android.foodorderapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.android.foodorderapp.databinding.ActivityRegisterBinding;
import com.android.foodorderapp.model.Account;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisterActivity extends AppCompatActivity {
    private ActivityRegisterBinding registerBinding;
    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerBinding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(registerBinding.getRoot());

        registerBinding.txtLogin.setOnClickListener(v->{
            finish();
        });

        registerBinding.btRegister.setOnClickListener(v->{
            String getUsername = registerBinding.edtUserNameRegister.getText().toString().trim();
            String getPassword = registerBinding.edtPasswordRegister1.getText().toString().trim();
            String getComfirmPass = registerBinding.edtPasswordRegister2.getText().toString().trim();
            String getName = registerBinding.edtName.getText().toString().trim();
            String getPhoneNumber = registerBinding.edtPhoneNumber.getText().toString().trim();
            String getAddress = registerBinding.edtAddress.getText().toString().trim();
            register(getUsername,getPassword,getComfirmPass,getName,getPhoneNumber,getAddress);
        });


    }

    private void register(String username,String pass,String comfirmPass,String name,String phoneNumber, String address){
        reference.child("account").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChild(username)){
                    Toast.makeText(RegisterActivity.this, "The account existed", Toast.LENGTH_SHORT).show();
                }else{
                    if(pass.equals(comfirmPass)){
                        Account account = new Account(username,pass,name,address,phoneNumber);
                        reference.child("account").child(username).setValue(account);
                        Toast.makeText(RegisterActivity.this, "Success", Toast.LENGTH_SHORT).show();
                        finish();
                    }else{
                        Toast.makeText(RegisterActivity.this, "The password does not equal the comfirm password", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        registerBinding = null;
    }
}