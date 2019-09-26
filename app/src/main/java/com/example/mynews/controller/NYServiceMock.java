package com.example.mynews.controller;

import com.example.mynews.model.Article;
import com.example.mynews.model.Data;
import com.example.mynews.model.DataPicture;
import com.example.mynews.model.Results;

import java.util.ArrayList;

import io.reactivex.Observable;
import retrofit2.mock.BehaviorDelegate;

public class NYServiceMock implements NYService {

    private BehaviorDelegate<NYService> mBehaviorDelegate;

    public NYServiceMock(BehaviorDelegate<NYService> behaviorDelegate) {
        mBehaviorDelegate = behaviorDelegate;
    }

    @Override
    public Observable<Results> searchArticle(String topic, String apikey) {
        ArrayList<Article> articles = new ArrayList<>();
        ArrayList<DataPicture> dp = new ArrayList<>();
        for (int j =0; j < 20; j++) {
            dp.add(new DataPicture("url"));
        }
        String resume = "";
        String date = "02/11/2008";
        String url = "https://www.nytimes.com/";
        for (int i=0; i<10; i++) {
            resume = "resume " + i;
            articles.add(new Article(url,resume,"Culture",date,dp));
        }

        Data data = new Data(articles);
        Results results = new Results("statusOK",data, new ArrayList<Article>());

        return  mBehaviorDelegate.returningResponse(results).searchArticle(topic,apikey);
    }

    @Override
    public Observable<Results> topArticle(String apikey) {
        return null;
    }

    @Override
    public Observable<Results> popularArticle(String apikey) {
        return null;
    }
}
