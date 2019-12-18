package com.example.mynews;

import android.util.Log;


import com.example.mynews.controller.NYService;
import com.example.mynews.model.Results;
import com.example.mynews.model.RetrofitClient;

import org.junit.BeforeClass;
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

    private static Results mResults0;
    private static Results mResults1;
    private static Results mResults2;

    @BeforeClass
    public static void initResults() {
        NYService nyService = RetrofitClient.getMock();

        Observable<Results> observable0 = nyService.popularArticle(NYService.APIKEY);
        Observable<Results> observable1 = nyService.topArticle("home", NYService.APIKEY);
        Observable<Results> observable2 = nyService.searchArticle("", NYService.APIKEY);

        System.out.println("initResults: -----------R0----------------");
        observable0.subscribeOn(Schedulers.trampoline())
                .observeOn(Schedulers.trampoline())
                .subscribe(new Observer<Results>() {

                    @Override
                    public void onSubscribe(Disposable d) {
                        System.out.println("suscribe");
                    }

                    @Override
                    public void onNext(Results results) {
                        System.out.println("next enter");
                        mResults0 = results;
                        if (mResults0 == null) {
                            System.out.println("mR0 NULL");
                        } else {
                            System.out.println("mR0 OK");
                        }
                        System.out.println("next exit");
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("erreur :" + e.toString());
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("complet");
                    }
                });

        System.out.println("initResults: -----------R1----------------");
        observable1.subscribeOn(Schedulers.trampoline())
                .observeOn(Schedulers.trampoline())
                .subscribe(new Observer<Results>() {

                    @Override
                    public void onSubscribe(Disposable d) {
                        System.out.println("suscribe");

                    }

                    @Override
                    public void onNext(Results results) {
                        System.out.println("next enter");
                        mResults1 = results;
                        if (mResults1 == null) {
                            System.out.println("mR1 NULL");
                        } else {
                            System.out.println("mR1 OK");
                        }System.out.println("next exit");
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("erreur :" + e.toString());
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("complet");

                    }
                });

        System.out.println("initResults: -----------R2----------------");
        observable2.subscribeOn(Schedulers.trampoline())
                .observeOn(Schedulers.trampoline())
                .subscribe(new Observer<Results>() {

                    @Override
                    public void onSubscribe(Disposable d) {
                        System.out.println("suscribe");

                    }

                    @Override
                    public void onNext(Results results) {
                        System.out.println("next enter");
                        mResults2 = results;
                        if (mResults2 == null) {
                            System.out.println("mR2 NULL");
                        } else {
                            System.out.println("mR2 OK");
                        }System.out.println("next exit");
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("erreur :" + e.toString());
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("complet");

                    }
                });

    }

    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
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