package com.example.mynews.model;

import io.reactivex.Observable;

public interface NYService {

   String APIKEY = "QXGOAUP24YZUfNIg4Drn3qaYAnpuV6dh" ;

    Observable<Results> popularArticle(String apikey);

    Observable<Results> topArticle(String theme, String apikey);

    Observable<Results> searchArticle(String query, String filter, String apikey);
}
