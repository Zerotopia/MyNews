package com.example.mynews.controller.broadcastreciever;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.mynews.R;
import com.example.mynews.controller.fragment.ApiFragment;

import java.util.Calendar;
import java.util.HashSet;

import static android.content.Context.MODE_PRIVATE;
import static com.example.mynews.controller.fragment.SearchFragment.CHANNEL;
import static com.example.mynews.controller.fragment.SearchFragment.KEYWORD;
import static com.example.mynews.controller.fragment.SearchFragment.NOTIFICATION_PARAM;
import static com.example.mynews.controller.fragment.SearchFragment.TOPICS;
import static com.example.mynews.controller.fragment.SearchFragment.REQUEST_CODE;
import static com.example.mynews.controller.fragment.SearchFragment.setSearchParameters;
import static com.example.mynews.model.FormatMaker.d8DateFormat;
import static com.example.mynews.model.FormatMaker.filterQueryFormat;


public class Reciever extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(NOTIFICATION_PARAM, MODE_PRIVATE);

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String date = (day) + "/" + (month + 1) + "/" + year;

       String[] intentExtra = setSearchParameters(
               sharedPreferences.getString(KEYWORD,""),
               d8DateFormat(date),
               "",
               filterQueryFormat(sharedPreferences.getStringSet(TOPICS, new HashSet<>()))
       );
        ApiFragment apiFragment = ApiFragment.newInstance(9, intentExtra);
        apiFragment.apiCall();
        int nbr = apiFragment.getNumberOfResults();
        Log.d("TAG", "onViewCreated: " + nbr);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        NotificationCompat.Builder notificationBuild =
                new NotificationCompat.Builder(context, CHANNEL)
                        .setSmallIcon(R.drawable.ic_attach_file_black_24dp)
                        .setContentTitle(context.getResources().getString(R.string.new_result))
                        .setContentText("Vous avez reçu " + nbr + " nouveaux resultats")
                        .setPriority(NotificationCompat.PRIORITY_HIGH);
        notificationManager.notify(REQUEST_CODE, notificationBuild.build());
    }
}
