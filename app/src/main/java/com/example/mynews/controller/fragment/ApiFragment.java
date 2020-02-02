package com.example.mynews.controller.fragment;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.idling.CountingIdlingResource;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager.widget.ViewPager.SimpleOnPageChangeListener;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mynews.R;
import com.example.mynews.controller.adapteur.ArticleAdapter;
import com.example.mynews.model.Article;
import com.example.mynews.model.Results;
import com.example.mynews.network.NYService;
import com.example.mynews.network.RetrofitClient;
import com.example.mynews.view.CustomItemDecoration;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;

//import static com.example.mynews.controller.fragment.AlertDialogFragment.NO_RESULT;
import static com.example.mynews.controller.fragment.AlertDialogFragment.HTTP_ERROR_400;
import static com.example.mynews.controller.fragment.AlertDialogFragment.HTTP_ERROR_429;
import static com.example.mynews.controller.fragment.AlertDialogFragment.HTTP_ERROR_500;
import static com.example.mynews.controller.fragment.AlertDialogFragment.NO_RESULT_MAIN;
import static com.example.mynews.controller.fragment.AlertDialogFragment.NO_RESULT_SEARCH;
import static com.example.mynews.controller.fragment.AlertDialogFragment.OTHER_ERROR;


/**
 * A simple {@link Fragment} subclass.
 */
public class ApiFragment extends Fragment implements Observer<Results> {


//    public interface NumberOfResultsListener {
//        void onNumberOfResultsChange(int numberOfResults, boolean resume);
//    }

    // private NumberOfResultsListener mNumberOfResultsListener;

    private final static String POSITION = "POSITION";
    private static final String PARAMETERS = "PARAMETERS";
    private static final String RXPROCESS = "RXPROCESS";

    private RecyclerView mRecyclerView;
    private int mPosition;
    private String[] mArguments;

    private boolean api = false;
    private boolean err = false;

    private int mNumberOfResults;

    private static boolean mTestMode = false;
    private boolean mViewMode = false;

    private static CountingIdlingResource mCount = new CountingIdlingResource(RXPROCESS);
    private int mUsage;

    @NonNull
    public static ApiFragment newInstance(int position, String[] parameters) {
        ApiFragment fragment = new ApiFragment();

        Bundle args = new Bundle();
        args.putInt(POSITION, position);
        args.putStringArray(PARAMETERS, parameters);
        fragment.setArguments(args);

        return fragment;
    }

//    @Override
//    public void onAttach(@NonNull Context context) {
//        super.onAttach(context);
//        mNumberOfResultsListener = (NumberOfResultsListener) context;
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflaterLayout = inflater.inflate(R.layout.fragment_api, container, false);
        mRecyclerView = inflaterLayout.findViewById(R.id.fragment_api_recyclerview);

        mViewMode = true;

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        if (getContext() != null)
            mRecyclerView.addItemDecoration(new CustomItemDecoration(getContext()));
        apiCall(Schedulers.io(), AndroidSchedulers.mainThread());
        Log.d("TAG", "onViewCreated: after call " + mNumberOfResults);
        Log.d("TAG", "onCreateView: API ");
        return inflaterLayout;
    }

//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//        //final RecyclerView recyclerView = view.findViewById(R.id.fragment_api_recyclerview);
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        if (getContext() != null)
//            mRecyclerView.addItemDecoration(new CustomItemDecoration(getContext()));
//        apiCall(Schedulers.io(), AndroidSchedulers.mainThread());
//        Log.d("TAG", "onViewCreated: after call " + mNumberOfResults);
//
//        //Log.d("TAG", "onViewCreated: Entrer : " + pos);
//
//    }

    public static CountingIdlingResource getCount() {
        return mCount;
    }

    // private String[] Subjects() {
    //    return getResources().getStringArray(R.array.subjects);
    //   }

    private void initArguments() {
        if (getArguments() != null) {
            mPosition = getArguments().getInt(POSITION, -1);
            mArguments = initParameters(getArguments().getStringArray(PARAMETERS));
        } else mPosition = -1;
    }

    private String[] initParameters(String[] args) {
        String[] parameters = new String[4];
        if (args != null) {
            for (int i = 0; i < 4; i++) {
                if (i < args.length) {
                    if ((args[i] != null) && (!args[i].isEmpty())) parameters[i] = args[i];
                    else parameters[i] = null;
                } else parameters[i] = null;
            }
        }
        String log = "";
        for (String par : parameters) {
            log += (par == null) ? "" : par;
        }
        Log.d("TAG", "initParameters: " + log);
        return parameters;
    }

    private Observable<Results> initObservable(int position, NYService nyService, String[] parameters) {
        Observable<Results> observable;
        String[] subjects = (isAdded()) ? getResources().getStringArray(R.array.subjects) : new String[0];

        if (position == 0)
            observable = nyService.topArticle("home", NYService.APIKEY);
        else if (position == 1) observable = nyService.popularArticle(NYService.APIKEY);
        else if (position < subjects.length)
            observable = nyService.topArticle(subjects[position].toLowerCase(), NYService.APIKEY);
        else observable = nyService.searchArticle(parameters[0],
                    parameters[1],
                    parameters[2],
                    parameters[3],
                    NYService.APIKEY);
        return observable;
    }

    public static void setTestMode(boolean testMode) {
        mTestMode = testMode;
    }


    public void apiCall(Scheduler subscribe, Scheduler observe) {
        Observable<Results> observable;
        NYService nyService = (mTestMode) ? RetrofitClient.getMock() : RetrofitClient.getInstance();
        //Log.d("TAG", "onViewCreated: debut observable");
        initArguments();
        Log.d("TAG", "onapicall: API" + mArguments[0] + mArguments[1] + mArguments[2] + mArguments[3]);
        mCount.increment();

        observable = initObservable(mPosition, nyService, mArguments);
//                Log.d("SWITCH", "onViewCreated: ANOMALIE");
        observable.subscribeOn(subscribe)
                .observeOn(observe)
                .unsubscribeOn(subscribe)
                .subscribe(this);
    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onResume() {
        Log.d("TAG", "onResume: aaaaaaaaaaaaapi" + api);
        noResultFound(api);
        api = false;
        httpErrorDialog(mUsage,err);
        err=false;
        super.onResume();
        //apiCall(Schedulers.io(), AndroidSchedulers.mainThread());

    }

    @Override
    public void onNext(Results results) {
        Log.d("TAG", "onClick: apicall rlient" + api);
        ArrayList<Article> articles = results.listOfArticle();
        mNumberOfResults = articles.size();
        if (mViewMode) {

            noResultFound(isResumed());
            mRecyclerView.setAdapter(new ArticleAdapter(articles));

        }
        Log.d("TAG", "onNext: apicall" + articles.size());
    }

    @Override
    public void onError(Throwable e) {
        if ((e instanceof HttpException)) {
            mUsage = ((HttpException) e).code();
            httpErrorDialog(mUsage, isResumed());
            // err = true;
        }
            Log.d("TAGERR", "onError: " + e.getMessage());
        e.printStackTrace();
        mCount.decrement();

    }

    @Override
    public void onComplete() {

        Log.d("TAG", "onComplete: ");
        mCount.decrement();
    }


    public int getNumberOfResults() {
        return mNumberOfResults;
    }

    private void noResultFound(boolean resume) {
        if ((mNumberOfResults == 0) && resume) {
             mUsage = (mPosition == 9) ? NO_RESULT_SEARCH : NO_RESULT_MAIN;
             generateAlertDialog();

        } else  api = true;
    }

    private void generateAlertDialog() {
        AlertDialogFragment alertDialogFragment = AlertDialogFragment.newInstance(mUsage);
        if (getActivity() != null)
            alertDialogFragment.show(getActivity().getSupportFragmentManager(), "KEY");
    }

    private void httpErrorDialog(int error, boolean resume) {
        if (resume) {
            if (500 <= error) {
                 mUsage = HTTP_ERROR_500;
                Log.d("TAGERR", "httpErrorDialog: 500" + error + " :::: " + mUsage);
            }
            else if (400 <= error) {
                mUsage = (error == 429) ? HTTP_ERROR_429 : HTTP_ERROR_400;
                Log.d("TAGERR", "httpErrorDialog: 400" + error + " :::: " + mUsage);
            }
            else mUsage = OTHER_ERROR;
            generateAlertDialog();
        } else err = true;
    }

    public void setNumberOfResults(int numberOfResults) {
        mNumberOfResults = numberOfResults;
    }
}