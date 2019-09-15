package com.example.testapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class SettingsActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    Switch switcheroo;
    SeekBar seekbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences=getSharedPreferences("settings",MODE_PRIVATE);
        setTheme(sharedPreferences.getBoolean("dark",true)? R.style.AppTheme : R.style.LightTheme);
        setContentView(R.layout.activity_settings);



        CardView darktheme=(CardView) findViewById(R.id.darktheme);
        switcheroo=(Switch) findViewById(R.id.darkthemeswitch);
        seekbar=(SeekBar) findViewById(R.id.seekBar);
        initsettings();
        darktheme.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View view) {
                                             changeTheme();

                                         }
                                     });
        switcheroo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeTheme();

            }
        });
        seekbar.setMax(45);
        seekbar.setMin(5);
        seekbar.setProgress(sharedPreferences.getInt("textsize",26));
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                sharedPreferences.edit().putInt("textsize",progress).commit();
                sharedPreferences.edit().putBoolean("changesapplied",false).commit();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }
    public void changeTheme(){

        sharedPreferences.edit().putBoolean("dark", !sharedPreferences.getBoolean("dark", true)).commit();
        startActivity(new Intent(SettingsActivity.this,SettingsActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
        finish();

    }
    public void initsettings(){
        switcheroo.setChecked(sharedPreferences.getBoolean("dark",true));
    }
}
