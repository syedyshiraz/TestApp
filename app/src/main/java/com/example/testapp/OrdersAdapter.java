package com.example.testapp;

//RecyclerViewadapter for IEEE and Adhamya!

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.MyViewHolder>{
    List<String> clubs;
    Activity activity;
    Context context;
    int latest;
    ViewGroup.LayoutParams mParams;
    public OrdersAdapter(List<String> mclubs, Activity activity, Context context){
        this.clubs=mclubs;
        this.activity=activity;
        this.context=context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.orderscard,parent,false);
        return new MyViewHolder(v);
    }



    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        final String imagename=clubs.get(position);

        FirebaseDatabase.getInstance().getReference("Approved_Orders").child("latest").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                latest=Integer.parseInt(dataSnapshot.getValue().toString());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase.getInstance().getReference("Approved_Orders").child(Integer.toString(latest)).child("location").setValue(holder.textView.toString());



            }
        });


    }



    @Override
    public int getItemCount() {
        return clubs.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView textViewororder;
        TextView textView;
        CardView cardView;
        public MyViewHolder(View v){
            super(v);
            textViewororder=v.findViewById(R.id.orders);
            textView=v.findViewById(R.id.textView);
            cardView=v.findViewById(R.id.card);
        }
    }
}
