package com.example.mynews.model;

import com.example.mynews.controller.NYService;
import com.example.mynews.controller.NYServiceMock;

import java.util.concurrent.TimeUnit;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.mock.BehaviorDelegate;
import retrofit2.mock.MockRetrofit;
import retrofit2.mock.NetworkBehavior;

public class RetrofitClient {

    private static Retrofit buildRetrofit() {
        return new Retrofit.Builder()
                .baseUrl("https://www.api.nytimes.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public  static NYService getInstance() {
        return buildRetrofit().create(NYService.class);
    }

    public  static NYService getMock() {

        NetworkBehavior localNetwork = NetworkBehavior.create();
        localNetwork.setDelay(1000, TimeUnit.MILLISECONDS);
        localNetwork.setVariancePercent(90);

        MockRetrofit mockRetrofit = new MockRetrofit.Builder(buildRetrofit())
                .networkBehavior(localNetwork)
                .build();

        BehaviorDelegate<NYService> delegate = mockRetrofit.create(NYService.class);

        return new NYServiceMock(delegate);
    }

}

