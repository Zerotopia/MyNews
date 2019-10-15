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

    private Results mResults0;
    private Results mResults1;
    private Results mResults2;

    @Before
    public void initResults() {
        NYService nyService = RetrofitClient.getMock();

        Observable<Results> observable0 = nyService.popularArticle(NYService.APIKEY);
        Observable<Results> observable1 = nyService.topArticle(NYService.APIKEY);
        Observable<Results> observable2 = nyService.searchArticle("", NYService.APIKEY);

        observable0.subscribeOn(Schedulers.trampoline())
                .observeOn(Schedulers.trampoline())
                .subscribe(new Observer<Results>() {

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Results results) {
                        mResults0 = results;
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {

                    }
                });

        observable1.subscribeOn(Schedulers.trampoline())
                .observeOn(Schedulers.trampoline())
                .subscribe(new Observer<Results>() {

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Results results) {
                        mResults1 = results;
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {

                    }
                });

        observable2.subscribeOn(Schedulers.trampoline())
                .observeOn(Schedulers.trampoline())
                .subscribe(new Observer<Results>() {

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Results results) {
                        mResults2 = results;
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
        assertEquals(5, 2 + 2);
    }

    @Test
    public void test_status() {
        assertEquals("statusOK", mResults0.getStatus());
    }


    @Test
    public void test_url() {
        String url =  mResults1.listArticle().get(0).urlArticle();
        assertEquals("https://www.nytimes.com/", url);
    }

    @Test
    public void test_title() {
        String title =  mResults2.listArticle().get(4).topicArticle();
        assertEquals("Culture > Theatre", title);
    }

    @Test
    public void test_resume0() {
        String resume = mResults0.listArticle().get(8).resumeArticle();
        assertEquals("POPULAR : 8", resume);
    }


    @Test
    public void test_resume1() {
        String resume = mResults1.listArticle().get(5).resumeArticle();
        assertEquals("TOP : 5", resume);
    }


    @Test
    public void test_resume2() {
        String resume = mResults2.listArticle().get(9).resumeArticle();
        assertEquals("SEARCH : 9", resume);
    }


}