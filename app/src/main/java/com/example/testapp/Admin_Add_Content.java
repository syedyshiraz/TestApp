package com.example.testapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class Admin_Add_Content extends AppCompatActivity {

    TextView product,date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Boolean darktheme=getSharedPreferences("settings",MODE_PRIVATE).getBoolean("dark",true);
        setTheme(darktheme ?R.style.AppTheme:R.style.LightTheme);
        setContentView(R.layout.activity_admin__add__content);
    }
}
