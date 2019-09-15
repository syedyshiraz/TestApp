package com.example.testapp;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    Location thelocation;

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

        startActivity(new Intent(this, MarketPlace.class));

       /* LocationManager locationManager=(LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.getAllProviders();
        if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);


        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Toast.makeText(getApplicationContext(),location.getLatitude()+" sixtynine "+location.getLongitude(),Toast.LENGTH_LONG).show();
                thelocation=location;
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        });

        /*new NotificationsClass("tits","this is",getApplicationContext(),null,null,getIntent(),1,"").send();

        new AlertDialog.Builder(this).setTitle("Yeah").setMessage("Why even").setNegativeButton("closethisshit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        }).setPositiveButton("yeah", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startActivity(Intent.createChooser(new Intent().setAction(Intent.ACTION_SEND).setType("text/plain").putExtra(Intent.EXTRA_TEXT,"yeahh"),null));
            }
        }).show();

        */
    }
}
