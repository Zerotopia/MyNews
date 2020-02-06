package com.example.mynews.network;

import com.example.mynews.model.Results;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Interface use by retrofit to perform API calls.
 */
public interface NYService {

    String APIKEY = "QXGOAUP24YZUfNIg4Drn3qaYAnpuV6dh";
    String[] NEWS_DESK = {"home", "science", "technology", "books", "travel", "food", "health"};

    @GET("search/v2/articlesearch.json")
    Observable<Results> searchArticle(
            @Query(value = "q", encoded = true) String topic,
            @Query("begin_date") String beginDate,
            @Query("end_date") String endDate,
            @Query("fq") String filter,
            @Query("api-key") String apikey);

    @GET("mostpopular/v2/emailed/7.json")
    Observable<Results> popularArticle(@Query("api-key") String apikey);

    @GET("topstories/v2/{theme}.json")
    Observable<Results> topArticle(@Path("theme") String theme, @Query("api-key") String apikey);
}
