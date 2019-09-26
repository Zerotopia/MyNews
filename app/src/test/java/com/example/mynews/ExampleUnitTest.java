package com.example.mynews;

import android.util.Log;

import com.example.mynews.controller.NYService;
import com.example.mynews.model.Results;
import com.example.mynews.model.RetrofitClient;

import org.junit.Before;
import org.junit.Test;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    private Results mResults;

    @Before
    public void initResults() {
        NYService nyService = RetrofitClient.getMock();

        Observable<Results> observable = nyService.searchArticle("", NYService.APIKEY);
        observable.subscribeOn(Schedulers.trampoline())
                .observeOn(Schedulers.trampoline())
                .subscribe(new Observer<Results>() {

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Results results) {
                        mResults = results;
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void test_status() {
        assertEquals("statusOK", mResults.getStatus());
    }


    @Test
    public void test_url() {
        String url =  mResults.getResponse().getDocs().get(0).getUrl();
        assertEquals("https://www.nytimes.com/", url);
    }

    @Test
    public void test_title() {
        String title =  mResults.getResponse().getDocs().get(4).getHeadline().getMain();
        assertEquals("Titre 4", title);
    }

    @Test
    public void test_resume() {
        String resume = mResults.getResponse().getDocs().get(9).getSnippet();
        assertEquals("resume 9", resume);
    }



}