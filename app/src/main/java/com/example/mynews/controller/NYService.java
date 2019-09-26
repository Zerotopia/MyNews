package com.example.mynews.controller;

import com.example.mynews.model.Results;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NYService {

    String APIKEY = "QXGOAUP24YZUfNIg4Drn3qaYAnpuV6dh";

    @GET("https://api.nytimes.com/svc/search/v2/articlesearch.json/")
    Observable<Results> searchArticle(@Query(value = "q", encoded = true) String topic, @Query("api-key") String apikey);

    @GET("https://api.nytimes.com/svc/mostpopular/v2/viewed/1.json")
    Observable<Results> popularArticle(@Query("api-key") String apikey);

    @GET("https://api.nytimes.com/svc/topstories/v2/home.json")
    Observable<Results> topArticle(@Query("api-key") String apikey);
}
