package com.example.testapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(getSharedPreferences("settings", MODE_PRIVATE).getBoolean("dark", true) ? R.style.AppTheme : R.style.LightTheme);
        if(FirebaseAuth.getInstance().getCurrentUser()==null){
            startActivity(new Intent(this,LoginActivity.class));
            finish();
        }
        else
            Toast.makeText(getApplicationContext(),FirebaseAuth.getInstance().getUid(),Toast.LENGTH_LONG).show();
        setContentView(R.layout.activity_main);

        new NotificationsClass("tits","this is",getApplicationContext(),null,null,getIntent(),1,"").send();


    }
}
