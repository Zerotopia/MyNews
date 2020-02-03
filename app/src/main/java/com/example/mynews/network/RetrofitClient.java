package com.example.mynews.network;

import java.util.concurrent.TimeUnit;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.mock.BehaviorDelegate;
import retrofit2.mock.MockRetrofit;
import retrofit2.mock.NetworkBehavior;

/**
 * Here we define the retrofit client.
 * The method getMock is use for test.
 */
public class RetrofitClient {

    private static final String API_BASE_URL = "https://api.nytimes.com/svc/";

    private static Retrofit buildRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public static NYService getMock() {
        NetworkBehavior localNetwork = NetworkBehavior.create();
        localNetwork.setDelay(1000, TimeUnit.MILLISECONDS);
        localNetwork.setVariancePercent(90);
        localNetwork.setFailurePercent(0);

        MockRetrofit mockRetrofit = new MockRetrofit.Builder(buildRetrofit())
                .networkBehavior(localNetwork)
                .build();

        BehaviorDelegate<NYService> delegate = mockRetrofit.create(NYService.class);
        return new NYServiceMock(delegate);
    }

    public static NYService getInstance() {
        return buildRetrofit().create(NYService.class);
    }
}
