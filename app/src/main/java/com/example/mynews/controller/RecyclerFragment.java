package com.example.mynews.controller;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.test.espresso.idling.CountingIdlingResource;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mynews.R;
import com.example.mynews.model.Article;
import com.example.mynews.model.Results;
import com.example.mynews.model.RetrofitClient;
import com.example.mynews.view.CustomItemDecoration;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class RecyclerFragment extends Fragment {

    private final static String POSITION = "posiion";
    private static CountingIdlingResource mCount = new CountingIdlingResource("RXPROCESS");

    public static CountingIdlingResource getCount() {
        return mCount;
    }

    @NonNull
    public static RecyclerFragment newInstance(int position) {
        RecyclerFragment fragment = new RecyclerFragment();

        Bundle args = new Bundle();
        args.putInt(POSITION, position);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recycler, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final RecyclerView rv = view.findViewById(R.id.recyclerview);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.addItemDecoration(new CustomItemDecoration(getContext()));

         NYService nyService = RetrofitClient.getInstance();
        //NYService nyService = RetrofitClient.getMock();
        int pos = (getArguments() == null) ? -1 : getArguments().getInt(POSITION,-1);


        Log.d("TAG", "onViewCreated: Entrer : " + pos);

        Observable<Results> observable;

        Log.d("TAG", "onViewCreated: debut observable");
        mCount.increment();
        switch (pos) {
            case 0:
                observable = nyService.popularArticle(NYService.APIKEY);
                break;
            case 1:
                observable = nyService.topArticle("home", NYService.APIKEY);
                break;
            case 2:
                observable = nyService.topArticle("books", NYService.APIKEY);
                break;
            default:
                observable = nyService.topArticle("home", NYService.APIKEY);
                Log.d("SWITCH", "onViewCreated: ANOMALIE");
        }
        //Observable<Results> observable = nyService.searchArticle("math", NYService.APIKEY);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Results>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Results results) {
                        Log.d("TAG", "onClick: rlient");

                        ArrayList<Article> art = results.listArticle();
//                        if (results.getResponse() != null) {
//                            Log.d("TAG", "onNext() called with: results = [" + results + "]");
//                            art = results.getResponse().getDocs();
//                        }
//                        else {
//                            Log.d("TAG", "onNext: else");
//                            art = results.getReponse2();
//                        }
                        Log.d("TAG", "onNext: " + art.size());
                        rv.setAdapter(new ArticlesAdapter(art));
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("TAG", "onError: " + e.getMessage());
                        mCount.decrement();
                    }

                    @Override
                    public void onComplete() {
                        mCount.decrement();

                    }
                });

    }
}
