package com.example.mynews.controller.broadcastreciever;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.mynews.R;
import com.example.mynews.controller.fragment.ApiFragment;

import java.util.Calendar;


public class Reciever extends BroadcastReceiver {
   public static final String CHANNEL = "NotificationChannel";



    @Override
    public void onReceive(Context context, Intent intent) {
       /* String[] intentExtra = new String[4];
        intentExtra[0] = mSearchText.getText().toString();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String date = (day) + "/" + (month + 1) + "/" + year;
        intentExtra[1] = d8DateFormat(date);
        intentExtra[2] = "";
        intentExtra[3] = filterQueryFormat();
        ApiFragment apiFragment = ApiFragment.newInstance(9, intentExtra);
        apiFragment.apiCall();
        int nbr = apiFragment.getNumberOfResults();
        Log.d("TAG", "onViewCreated: " + nbr);
*/
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        NotificationCompat.Builder notificationBuild =
                new NotificationCompat.Builder(context, CHANNEL)
                        .setSmallIcon(R.drawable.ic_attach_file_black_24dp)
                        .setContentTitle(context.getResources().getString(R.string.new_result))
                        .setContentText("Vous avez reçu " + "8" + " nouveaux resultats")
                        .setPriority(NotificationCompat.PRIORITY_HIGH);
        notificationManager.notify(42, notificationBuild.build());
    }
}
