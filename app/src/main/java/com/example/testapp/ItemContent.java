package com.example.testapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

public class ItemContent extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Boolean darktheme=getSharedPreferences("settings",MODE_PRIVATE).getBoolean("dark",true);
        setTheme(darktheme ?R.style.AppTheme:R.style.LightTheme);
        setContentView(R.layout.activity_item_content);

        Toast.makeText(getApplicationContext(),"yeah",Toast.LENGTH_LONG).show();
    }
}
