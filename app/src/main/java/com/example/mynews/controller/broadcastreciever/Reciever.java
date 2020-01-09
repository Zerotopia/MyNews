package com.example.mynews.controller.broadcastreciever;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.mynews.R;


public class Reciever extends BroadcastReceiver {
   public static final String CHANNEL = "NotificationChannel";

    @Override
    public void onReceive(Context context, Intent intent) {

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        NotificationCompat.Builder notificationBuild =
                new NotificationCompat.Builder(context, CHANNEL)
                        .setSmallIcon(R.drawable.ic_attach_file_black_24dp)
                        .setContentTitle(context.getResources().getString(R.string.new_result))
                        .setContentText("premiere notification")
                        .setPriority(NotificationCompat.PRIORITY_HIGH);
        notificationManager.notify(42, notificationBuild.build());
    }
}
