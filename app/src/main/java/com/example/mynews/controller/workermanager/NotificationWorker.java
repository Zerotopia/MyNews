package com.example.mynews.controller.workermanager;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.mynews.R;
import com.example.mynews.controller.fragment.ApiFragment;

import java.util.Calendar;
import java.util.HashSet;

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

/**
 * This class implement the work that Workmanager should do in background,
 * i.e. in our case send a notification when new article are found.
 */
public class NotificationWorker extends Worker {

    private Context mContext;

    public NotificationWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        mContext = context;
    }

    /**
     * 1- We recover from SharedPreferences all information that we need to send the
     * notification's request.
     * 2- We perform the API call with trampoline Schedulers to ensure that the API call
     * is finished when we call the method getNumberOfArticles.
     * 3- Finally we build the notification if the number of articles found is not zero.
     */
    @NonNull
    @Override
    public Result doWork() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(NOTIFICATION_PARAM, MODE_PRIVATE);

        String beginDate = getBeginDate();
        String[] intentExtra = setSearchParameters(
                sharedPreferences.getString(KEYWORD, ""),
                beginDate,
                "",
                filterQueryFormat(sharedPreferences.getStringSet(TOPICS, new HashSet<>()))
        );

        ApiFragment apiFragment = ApiFragment.newInstance(mContext.getResources().getStringArray(R.array.subjects).length, intentExtra);
        apiFragment.apiCall(Schedulers.trampoline(), Schedulers.trampoline());

        buildNotification(apiFragment.getNumberOfResults());

        return Result.success();
    }

    /**
     * We use the result of this function to determine the "begin_date" argument of
     * the api call. Hence the api return only the articles that are published in
     * the last 24 hours.
     *
     * @return the yesterday of the current date in the appropriate format.
     */
    private String getBeginDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return d8DateFormat(mContext.getString(R.string.date_format, day, (month + 1), year));
    }

    private void buildNotification(int numberOfArticles) {
        if (numberOfArticles != 0) {
            Resources resources = mContext.getResources();
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(mContext);
            NotificationCompat.Builder notificationBuild =
                    new NotificationCompat.Builder(mContext, CHANNEL)
                            .setSmallIcon(R.drawable.ic_attach_file_black_24dp)
                            .setContentTitle(resources.getQuantityString(
                                    R.plurals.new_articles,
                                    numberOfArticles))
                            .setContentText(resources.getQuantityString(
                                    R.plurals.notification_message,
                                    numberOfArticles,
                                    numberOfArticles))
                            .setPriority(NotificationCompat.PRIORITY_HIGH);
            notificationManager.notify(REQUEST_CODE, notificationBuild.build());
        }
    }
}
