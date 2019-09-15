package com.example.testapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SubMainProducts extends AppCompatActivity {

    RecyclerView recyclerView;
    MarketPlaceAdapter marketPlaceAdapter;
    List<String> clubs=new ArrayList<>();
    String category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_main_products);

        recyclerView=findViewById(R.id.recylerview);
        RecyclerView.LayoutManager layoutManager=new GridLayoutManager(this,4);
        recyclerView.setLayoutManager(layoutManager);
        marketPlaceAdapter=new MarketPlaceAdapter(clubs,this,this);
        recyclerView.setAdapter(marketPlaceAdapter);
        populateTheGrid();
    }

    public void populateTheGrid(){
        clubs.add("spaceForLayoutThisIsn'taClubExactlyBut");
        clubs.add("spaceForLayoutThisIsn'taClubExactlyBut");
        clubs.add("spaceForLayoutThisIsn'taClubExactlyBut");
        clubs.add("spaceForLayoutThisIsn'taClubExactlyBut");
        category=getIntent().getStringExtra("subcategory");
        Toast.makeText(getApplicationContext(),category,Toast.LENGTH_LONG).show();
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("Products").child(category.trim());
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
