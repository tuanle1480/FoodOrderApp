package com.android.foodorderapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.foodorderapp.databinding.ActivityLoginBinding;
import com.android.foodorderapp.model.Account;
import com.android.foodorderapp.model.Hours;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding loginBinding;
    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginBinding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(loginBinding.getRoot());


        loginBinding.txtRegister.setOnClickListener(v->{
            startActivity(new Intent(this,RegisterActivity.class));
        });

        loginBinding.btLogin.setOnClickListener(v->{
            String getUsername = loginBinding.edtUserNameLogin.getText().toString().trim();
            String getPassword = loginBinding.edtPasswordLogin.getText().toString().trim();
            login(getUsername,getPassword);
        });


    }

    private void login(String username, String pass){
        reference.child("account").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChild(username)){
                    reference.child("account").child(username).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            Account account = snapshot.getValue(Account.class);
                            if(account.getPassword().equals(pass)) {
                                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                                intent.putExtra("username",account.getUsername());
                                intent.putExtra("name",account.getName());
                                intent.putExtra("address",account.getAddress());
                                intent.putExtra("phone",account.getPhoneNumber());
                                startActivity(intent);
                            }else{
                                Toast.makeText(LoginActivity.this, "The password is wrong", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }else{
                    Toast.makeText(LoginActivity.this, "The account does not exist", Toast.LENGTH_SHORT).show();
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
        loginBinding = null;
    }
}