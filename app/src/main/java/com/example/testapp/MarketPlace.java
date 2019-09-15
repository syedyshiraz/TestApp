package com.example.testapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MarketPlace extends AppCompatActivity {

    RecyclerView recyclerView;
    MarketPlaceAdapter marketPlaceAdapter;
    List<String> clubs=new ArrayList<>();
    NavigationView mNavigationView;
    DrawerLayout mDrawerLayout;
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market_place);

        mToolbar=findViewById(R.id.toolbar_main);
        setSupportActionBar(mToolbar);

        mDrawerLayout=findViewById(R.id.navigation_drawer_main);
        mNavigationView=findViewById(R.id.navigation_view);

        mDrawerLayout.requestDisallowInterceptTouchEvent(true);

        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id;
                Intent intent;
                id=menuItem.getItemId();
                switch (id)
                {

                    case R.id.orders:
                        startActivity(new Intent(MarketPlace.this,Orders.class));
                        break;
                    case R.id.settings:
                        startActivity(new Intent(MarketPlace.this,SettingsActivity.class));
                        break;
                    case R.id.about:
                        startActivity(new Intent(MarketPlace.this,About.class));
                        break;
                }
                mDrawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,mDrawerLayout,mToolbar,0,0);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        recyclerView=findViewById(R.id.recylerview);
        RecyclerView.LayoutManager layoutManager=new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);
        marketPlaceAdapter=new MarketPlaceAdapter(clubs,this,this);
        recyclerView.setAdapter(marketPlaceAdapter);
        populateTheGrid();
    }

    public void populateTheGrid(){
        clubs.add("spaceForLayoutThisIsn'taClubExactlyBut");
        clubs.add("spaceForLayoutThisIsn'taClubExactlyBut");
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("Products");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                clubs.clear();
                marketPlaceAdapter.notifyDataSetChanged();
                for(DataSnapshot childsnapshot: dataSnapshot.getChildren()) {
                    clubs.add(childsnapshot.getKey());
                    marketPlaceAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
