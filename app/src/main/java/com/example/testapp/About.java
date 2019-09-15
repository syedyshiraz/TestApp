package com.example.testapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class About extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Boolean darktheme=getSharedPreferences("settings",MODE_PRIVATE).getBoolean("dark",true);
        setTheme(darktheme ?R.style.AppTheme:R.style.LightTheme);
        setContentView(R.layout.activity_about);
    }
}
