package com.example.mynews.controller;

import com.example.mynews.model.Results;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface NYService {

    String APIKEY = "QXGOAUP24YZUfNIg4Drn3qaYAnpuV6dh";

    @GET("https://api.nytimes.com/svc/search/v2/articlesearch.json/")
    Observable<Results> searchArticle(
            @Query(value = "q", encoded = true) String topic,
            @Query("begin_date") String beginDate,
            @Query("end_date") String endDate,
            @Query("fq") String theme,
            @Query("api-key") String apikey);

    @GET("https://api.nytimes.com/svc/mostpopular/v2/viewed/1.json")
    Observable<Results> popularArticle(@Query("api-key") String apikey);

    @GET("https://api.nytimes.com/svc/topstories/v2/{topic}.json")
    Observable<Results> topArticle(@Path("topic") String topic, @Query("api-key") String apikey);
}
