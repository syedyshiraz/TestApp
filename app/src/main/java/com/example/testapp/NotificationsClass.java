package com.example.testapp;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.bumptech.glide.request.FutureTarget;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.concurrent.ExecutionException;

public class NotificationsClass {

    Bitmap largeNotificaitonImage,veryLargeNotificationImage;
    Context context;
    Activity activity;
    Intent intent;
    String title,body,image;
    int misc;

    public NotificationsClass(String title, String body, Context context, Bitmap largeNotificaitonImage, Bitmap veryLargeNotificationImage, Intent intent, int misc, String image){
        this.title=title;
        this.body=body;
        this.context=context;
        this.largeNotificaitonImage=largeNotificaitonImage;
        this.intent=intent;
        this.misc=misc;
        this.veryLargeNotificationImage=veryLargeNotificationImage;
        this.image=image;

    }


    public void send() {


        NotificationManager notificationManager = (NotificationManager) context.getSystemService(context.getApplicationContext().NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel("uvce_connect_main_channel", "UVCE CONNECT", NotificationManager.IMPORTANCE_MAX);

            // Configure the notification channel.
            notificationChannel.setDescription("Notifications about college stuff");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.setVibrationPattern(new long[]{0, 700, 200, 700});
            notificationChannel.enableVibration(true);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        NotificationCompat.Builder notificaitonBuilder = new NotificationCompat.Builder(context, "uvce_connect_main_channel");


        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context.getApplicationContext());
        stackBuilder.addNextIntentWithParentStack(intent);
        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        //Actual notification builder


        notificaitonBuilder.setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(body)
                .setContentInfo("Does this matter?")
                .setLargeIcon(largeNotificaitonImage);

        if (!image.isEmpty()) {
            StorageReference imageref = FirebaseStorage.getInstance().getReference().child(image);
            FutureTarget<Bitmap> futureBitmap = GlideApp.with(context.getApplicationContext()).asBitmap().load(imageref).submit(700, 350);
            try {
                veryLargeNotificationImage = futureBitmap.get();
            } catch (ExecutionException e) {
            } catch (InterruptedException e1) {
            }
            NotificationCompat.BigPictureStyle style = new NotificationCompat.BigPictureStyle()
                    .bigLargeIcon(largeNotificaitonImage)
                    .bigPicture(veryLargeNotificationImage);
            notificaitonBuilder.setStyle(style);
            futureBitmap.cancel(false);
        }


        notificationManager.notify((int) System.currentTimeMillis(), notificaitonBuilder.build());
    }
}
