package com.example.mynews.controller;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.IdlingResource;
import androidx.test.espresso.idling.CountingIdlingResource;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mynews.R;
import com.example.mynews.model.Article;
import com.example.mynews.model.Results;
import com.example.mynews.network.NYService;
import com.example.mynews.network.RetrofitClient;
import com.example.mynews.view.CustomItemDecoration;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class ApiFragment extends Fragment {


    private final static String POSITION = "posiion";
    private static final String PARAMETERS = "parameters";
    private static CountingIdlingResource mCount = new CountingIdlingResource("RXPROCESS");

    @NonNull
    public static ApiFragment newInstance(int position, String[] parameters) {
        ApiFragment fragment = new ApiFragment();

        Bundle args = new Bundle();
        args.putInt(POSITION, position);
        args.putStringArray(PARAMETERS, parameters);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_api, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final RecyclerView recyclerView = view.findViewById(R.id.fragment_api_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new CustomItemDecoration(getContext()));

        NYService nyService = RetrofitClient.getInstance();
        int position = (getArguments() == null) ? -1 : getArguments().getInt(POSITION, -1);
        String[] parameters = new String[4];
        if (getArguments() != null) {
            String[] args = getArguments().getStringArray(PARAMETERS);
            if (args != null) {
                for (int i = 0; i < 4; i++) {
                    if ((i < args.length) && (!args[i].isEmpty())) parameters[i] = args[i];
                    else parameters[i] = null;
                }
            }
        }

        //Log.d("TAG", "onViewCreated: Entrer : " + pos);
        Observable<Results> observable;

        //Log.d("TAG", "onViewCreated: debut observable");
        mCount.increment();
        if (position == -1)
            observable = nyService.searchArticle(parameters[0],
                    parameters[1],
                    parameters[2],
                    parameters[3],
                    NYService.APIKEY);
        else if (position == 0) observable = nyService.popularArticle(NYService.APIKEY);
        else if (position == 1) observable = nyService.topArticle("home", NYService.APIKEY);
        else observable = nyService.topArticle(parameters[0], NYService.APIKEY);
//                Log.d("SWITCH", "onViewCreated: ANOMALIE");
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Results>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Results results) {
                        //   Log.d("TAG", "onClick: rlient");

                        ArrayList<Article> articles = results.listOfArticle();
//                        if (results.getResponse() != null) {
//                            Log.d("TAG", "onNext() called with: results = [" + results + "]");
//                            art = results.getResponse().getDocs();
//                        }
//                        else {
//                            Log.d("TAG", "onNext: else");
//                            art = results.getReponse2();
//                        }
                        //      Log.d("TAG", "onNext: " + art.size());
                        recyclerView.setAdapter(new ArticleAdapter(articles));
                    }

                    @Override
                    public void onError(Throwable e) {
                        //      Log.d("TAG", "onError: " + e.getMessage());
                        mCount.decrement();
                    }

                    @Override
                    public void onComplete() {
                        mCount.decrement();

                    }
                });
    }

    public static CountingIdlingResource getmCount() {
        return mCount;
    }
}
