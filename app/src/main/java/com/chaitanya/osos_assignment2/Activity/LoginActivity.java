package com.chaitanya.osos_assignment2.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.chaitanya.osos_assignment2.R;

public class LoginActivity extends AppCompatActivity {

    private TextView tvUsername, tvPassword;
    private Button btn_login;

    SharedPreferences sharedPreferences;

    public static final String fileName = "login";
    public static final String Username = "username";
    public static final String Password = "password";

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tvUsername = findViewById(R.id.tv_username);
        tvPassword = findViewById(R.id.tv_password);

        btn_login = findViewById(R.id.btn_login);

        sharedPreferences = getSharedPreferences(fileName, Context.MODE_PRIVATE);

        if(sharedPreferences.contains(Username) && sharedPreferences.contains(Password)){
            Intent i = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(i);
            finish();
        }

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = tvUsername.getText().toString();
                String password = tvPassword.getText().toString();

                if(username.equals("123") && password.equals("123")){
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(Username,username);
                    editor.putString(Password,password);
                    editor.commit();

                    Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();

                    Intent i = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(i);
                    finish();
                }else{
                    Toast.makeText(LoginActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();

                    tvUsername.setText("");
                    tvPassword.setText("");
                }

            }
        });
    }
}