package com.example.mynews.network;

import com.example.mynews.model.Article;
import com.example.mynews.model.DataPicture;
import com.example.mynews.model.Results;

import java.util.ArrayList;

import io.reactivex.Observable;
import retrofit2.mock.BehaviorDelegate;

import static com.example.mynews.model.Article.NYT_HOME_URL;

public class NYServiceMock implements NYService {

    private BehaviorDelegate<NYService> mBehaviorDelegate;

    public NYServiceMock(BehaviorDelegate<NYService> behaviorDelegate) {
        mBehaviorDelegate = behaviorDelegate;
    }

    @Override
    public Observable<Results> searchArticle(String topic, String beginDate, String endDate, String filtre, String apikey) {
        Results results = generateCode("SEARCH : ");
        return  mBehaviorDelegate.returningResponse(results).searchArticle(topic,beginDate,endDate,filtre,apikey);
    }

    @Override
    public Observable<Results> popularArticle(String apikey) {
        Results results = generateCode("POPULAR : ");
        return  mBehaviorDelegate.returningResponse(results).popularArticle(apikey);
    }

    @Override
    public Observable<Results> topArticle(String theme, String apikey) {
        Results results = generateCode("TOP : ");
        return  mBehaviorDelegate.returningResponse(results).topArticle(theme, apikey);
    }

    private Results generateCode(String nameService) {
        ArrayList<Article> articles = new ArrayList<>();
        ArrayList<DataPicture> dp = new ArrayList<>();
        for (int j =0; j < 4; j++) {
            dp.add(new DataPicture("https://www.picasso.fr"));
        }
        String summary;
        int nbResults = (nameService.equals("SEARCH : ")) ? 17 : 20;
        String date = "2002/11/21blabla";
        for (int i=0; i<nbResults; i++) {
            summary = nameService + i;
            articles.add(new Article(summary,"Culture","Theatre",date,dp, NYT_HOME_URL));
        }
        return new Results("statusOK",articles);
    }
}
