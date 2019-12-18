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
    public Observable<Results> searchArticle(String topic, String beginDate, String endDate, String theme, String apikey) {
        Results results = genrateCode("SEARCH : ");
        return  mBehaviorDelegate.returningResponse(results).searchArticle(topic,beginDate,endDate,theme,apikey);
    }

    @Override
    public Observable<Results> topArticle(String topic, String apikey) {
        Results results = genrateCode("TOP : ");
        return  mBehaviorDelegate.returningResponse(results).topArticle(topic, apikey);
    }

    @Override
    public Observable<Results> popularArticle(String apikey) {
        Results results = genrateCode("POPULAR : ");
        return  mBehaviorDelegate.returningResponse(results).popularArticle(apikey);
    }

    private Results genrateCode (String nameService) {
        ArrayList<Article> articles = new ArrayList<>();
        ArrayList<DataPicture> dp = new ArrayList<>();
        for (int j =0; j < 20; j++) {
            dp.add(new DataPicture("url"));
        }
        String resume;
        String date = "2002/11/21blabla";
        String url = "https://www.nytimes.com/";
        for (int i=0; i<10; i++) {
            resume = nameService + i;
            articles.add(new Article(url,resume,"Culture","Theatre",date,dp));
        }

        Data data = new Data(articles);
        return new Results("statusOK",data, new ArrayList<Article>());
    }
}
