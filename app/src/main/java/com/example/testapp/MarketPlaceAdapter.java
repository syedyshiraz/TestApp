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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class MarketPlaceAdapter extends RecyclerView.Adapter<MarketPlaceAdapter.MyViewHolder>{
    List<String> clubs;
    Activity activity;
    Context context;
    ViewGroup.LayoutParams mParams;
    public MarketPlaceAdapter(List<String> mclubs, Activity activity, Context context){
        this.clubs=mclubs;
        this.activity=activity;
        this.context=context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.girdcard,parent,false);
        return new MyViewHolder(v);
    }



    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        final String imagename=clubs.get(position);
        if(imagename.equals("spaceForLayoutThisIsn'taClubExactlyBut"))
            holder.space.setVisibility(View.VISIBLE);
        else {
            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(activity.getClass().getSimpleName().equals("MarketPlace")) {
                        Intent intent = new Intent(v.getContext(), SubMainProducts.class);
                        intent.putExtra("subcategory", imagename);
                        v.getContext().startActivity(intent);
                    }
                    else if(activity.getClass().getSimpleName().equals("SubMainProducts")){
                        v.getContext().startActivity(new Intent(v.getContext(),ProductPage.class).putExtra("product",imagename));
                    }
                    else{
                        v.getContext().startActivity(new Intent(v.getContext(),ItemContent.class).putExtra("subcategory", imagename));
                    }
                }
            });
            StorageReference storageReference = FirebaseStorage.getInstance().getReference("products/" + imagename + ".jpg");

            if(activity.getClass().getSimpleName().equals("SubMainProducts"))
                storageReference = FirebaseStorage.getInstance().getReference("subproducts/"+activity.getIntent().getStringExtra("subcategory") + "/" + imagename + ".jpg");



            GlideApp.with(context).load(storageReference).diskCacheStrategy(DiskCacheStrategy.ALL).override(holder.imageView.getWidth(),holder.imageView.getWidth()).fitCenter().listener(new RequestListener<Drawable>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                    return false;
                }

                @Override
                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                    holder.imageView.setMinimumHeight(holder.imageView.getWidth());
                    Log.e(imagename,Integer.toString(holder.imageView.getWidth()));
                    return false;
                }
            }).into(holder.imageView);

            holder.textView.setText(imagename);


        }
    }



    @Override
    public int getItemCount() {
        return clubs.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        Space space;
        TextView textView;
        public MyViewHolder(View v){
            super(v);
            imageView=v.findViewById(R.id.cardimageview);
            space=v.findViewById(R.id.space);
            textView=v.findViewById(R.id.itemname);
        }
    }
}
