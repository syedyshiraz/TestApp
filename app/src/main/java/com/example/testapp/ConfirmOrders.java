package com.example.testapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ConfirmOrders extends AppCompatActivity {

    RecyclerView recyclerView;
    OrdersAdapter ordersAdapter;
    List<String> clubs=new ArrayList<>();
    NavigationView mNavigationView;
    DrawerLayout mDrawerLayout;
    Toolbar mToolbar;
    Boolean darktheme;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_orders);

        recyclerView=findViewById(R.id.recylerview);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        ordersAdapter=new OrdersAdapter(clubs,this,this);
        recyclerView.setAdapter(ordersAdapter);
        populate();
    }

    public void populate(){
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("Orders");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                clubs.clear();
                ordersAdapter.notifyDataSetChanged();
                for(DataSnapshot childsnapshot: dataSnapshot.getChildren()) {
                    clubs.add(childsnapshot.getKey());
                    ordersAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
