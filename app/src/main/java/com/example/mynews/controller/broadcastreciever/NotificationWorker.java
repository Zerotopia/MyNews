package com.example.mynews.controller.broadcastreciever;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.mynews.R;
import com.example.mynews.controller.fragment.ApiFragment;

import java.util.Calendar;
import java.util.HashSet;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static android.content.Context.MODE_PRIVATE;
import static com.example.mynews.controller.fragment.SearchFragment.CHANNEL;
import static com.example.mynews.controller.fragment.SearchFragment.KEYWORD;
import static com.example.mynews.controller.fragment.SearchFragment.NOTIFICATION_PARAM;
import static com.example.mynews.controller.fragment.SearchFragment.REQUEST_CODE;
import static com.example.mynews.controller.fragment.SearchFragment.TOPICS;
import static com.example.mynews.controller.fragment.SearchFragment.setSearchParameters;
import static com.example.mynews.model.FormatMaker.d8DateFormat;
import static com.example.mynews.model.FormatMaker.filterQueryFormat;

public class NotificationWorker extends Worker {
    public NotificationWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        Context context = getApplicationContext();
        SharedPreferences sharedPreferences = context.getSharedPreferences(NOTIFICATION_PARAM, MODE_PRIVATE);

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -2);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String date = context.getString(R.string.date_format, day, (month + 1), year);

        String[] intentExtra = setSearchParameters(
                sharedPreferences.getString(KEYWORD, ""),
                d8DateFormat(date),
                "",
                filterQueryFormat(sharedPreferences.getStringSet(TOPICS, new HashSet<>()))
        );
        ApiFragment apiFragment = ApiFragment.newInstance(9, intentExtra);
        apiFragment.apiCall(Schedulers.trampoline(), Schedulers.trampoline());
        int nbr = 0;


        nbr = apiFragment.getNumberOfResults();



        Log.d("TAG", "onViated: " + nbr);

        if (nbr != 0) {
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
            NotificationCompat.Builder notificationBuild =
                    new NotificationCompat.Builder(context, CHANNEL)
                            .setSmallIcon(R.drawable.ic_attach_file_black_24dp)
                            .setContentTitle(context.getResources().getString(R.string.new_result))
                            .setContentText("Vous avez reçu " + nbr + " nouveaux resultats")
                            .setPriority(NotificationCompat.PRIORITY_HIGH);
            notificationManager.notify(REQUEST_CODE, notificationBuild.build());
        }
        return Result.success();
    }
}
