package com.example.mynews;


import com.example.mynews.model.Article;
import com.example.mynews.model.NYService;
import com.example.mynews.model.Results;
import com.example.mynews.model.RetrofitClient;
import com.google.gson.Gson;

import org.junit.BeforeClass;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

import javax.xml.transform.Result;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.example.mynews.model.Article.NYT_HOME_URL;
import static com.example.mynews.model.Article.UNDEFINED;
import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    private static Results sSearchJson;
    private static Results sPopularJson;
    private static Results sTopJson;

    private static Results sSearchResults;
    private static Results sPopularResults;
    private static Results sTopResults;

    private static final int SEARCH = 0;
    private static final int MOSTPOPULAR = 1;
    private static final int TOPARTICLE = 2;


    private static void rxJavaCall(Observable<Results> observable, final int api) {

        observable.subscribeOn(Schedulers.trampoline())
                .observeOn(Schedulers.trampoline())
                .subscribe(new Observer<Results>() {

                    @Override
                    public void onSubscribe(Disposable d) {
                        System.out.println("suscribe");
                    }

                    @Override
                    public void onNext(Results results) {
                        System.out.println("next enter");
                        if (api == SEARCH) sSearchResults = results;
                        else if (api == MOSTPOPULAR) sPopularResults = results;
                        else if(api == TOPARTICLE) sTopResults = results;
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

    @BeforeClass
    public static void setUp() throws FileNotFoundException {
        // set up variables to test the parsing Json files from POJO class
        Gson gson = new Gson();
        sSearchJson = gson.fromJson(new FileReader("search.json"), Results.class);
        sPopularJson = gson.fromJson(new FileReader("mostpopular.json"), Results.class);
        sTopJson = gson.fromJson(new FileReader("topstories.json"), Results.class);

        // set up variables to test retrofit client.
        NYService nyService = RetrofitClient.getMock();

       // Observable<Results> searchObs = nyService.searchArticle("math", "", NYService.APIKEY);
       // Observable<Results> popularObs = nyService.popularArticle(NYService.APIKEY);
       // Observable<Results> topObs = nyService.topArticle("home", NYService.APIKEY);

       // rxJavaCall(searchObs, SEARCH);
       // rxJavaCall(popularObs, MOSTPOPULAR);
       // rxJavaCall(topObs, TOPARTICLE);
    }

    @Test
    public void checkResultsModelForSearchJson(){
        assertEquals(sSearchJson.getStatus(),"OK");

        ArrayList<Article> Articles = sSearchJson.listOfArticle();

        Article article0 = Articles.get(0);
        assertEquals(article0.summary(),"search snippet0");
        assertEquals(article0.topics(),"search sectionname0 > search subsectionname0");
        assertEquals(article0.publishedDate(),"01/08/2014");
        assertEquals(article0.urlImage(),NYT_HOME_URL + "search urlimg0");
        assertEquals(article0.urlArticle(),"search url0");

        Article article1 = Articles.get(1);
        assertEquals(article1.summary(),"search abstract1");
        assertEquals(article1.topics(),"search sectionname1 > search newsdesk1");
        assertEquals(article1.publishedDate(),UNDEFINED);
        assertEquals(article1.urlImage(),UNDEFINED);
        assertEquals(article1.urlArticle(),UNDEFINED);

        Article article2 = Articles.get(2);
        assertEquals(article2.summary(),"search lpar2");
        assertEquals(article2.topics(),"search sectionname2 > search tom2");
        assertEquals(article2.publishedDate(),"17/07/2019");
        assertEquals(article2.urlImage(),NYT_HOME_URL + "search default url2");


        Article article3 = Articles.get(3);
        assertEquals(article3.summary(),"search main3");
        assertEquals(article3.topics(),"search sectionname3 > search dtype3");
        assertEquals(article3.urlImage(),UNDEFINED);

        Article article4 = Articles.get(4);
        assertEquals(article4.summary(),UNDEFINED);
        assertEquals(article4.topics(),"search subsectionname4 > search newsdesk4");

        assertEquals(Articles.get(5).topics(),"search subsectionname5 > search tom5");
        assertEquals(Articles.get(6).topics(),"search subsectionname6 > search dtype6");
        assertEquals(Articles.get(7).topics(),"search newsdesk7 > search tom7");
        assertEquals(Articles.get(8).topics(),"search newsdesk8 > search dtype8");
        assertEquals(Articles.get(9).topics(),"search tom9");
    }

    @Test
    public void checkResultsModelForMostPopularJson(){
        assertEquals(sPopularJson.getStatus(),"OK");

        ArrayList<Article> Articles = sPopularJson.listOfArticle();

        Article article0 = Articles.get(0);
        assertEquals(article0.summary(),"most abstract0");
        assertEquals(article0.topics(),"most section0 > most subsection0");
        assertEquals(article0.publishedDate(),"10/12/2019");
        assertEquals(article0.urlImage(),"most urlimg0");
        assertEquals(article0.urlArticle(),"most url0");

        Article article1 = Articles.get(1);
        assertEquals(article1.summary(),"most title1");
        assertEquals(article1.topics(),"most section1 > most type1");
        assertEquals(article1.publishedDate(),UNDEFINED);
        assertEquals(article1.urlImage(),"most default url1");
        assertEquals(article1.urlArticle(),UNDEFINED);

        Article article2 = Articles.get(2);
        assertEquals(article2.summary(),UNDEFINED);
        assertEquals(article2.topics(),"most subsection2 > most type2");
        assertEquals(article2.publishedDate(),"16/12/2019");
        assertEquals(article2.urlImage(),UNDEFINED);

        Article article3 = Articles.get(3);
        assertEquals(article3.topics(),"most section3");
        assertEquals(article3.urlImage(),"most default url3");

        Article article4 = Articles.get(4);
        assertEquals(article4.topics(),"most type4");
        assertEquals(article4.urlImage(),UNDEFINED);

        assertEquals(Articles.get(5).topics(),UNDEFINED);
    }

    @Test
    public void checkResultsModelForTopStoriesJson(){
        assertEquals(sTopJson.getStatus(),"OK");

        ArrayList<Article> Articles = sTopJson.listOfArticle();

        Article article0 = Articles.get(0);
        assertEquals(article0.summary(),"top abstract0");
        assertEquals(article0.topics(),"top section0 > top subsection0");
        assertEquals(article0.publishedDate(),"11/12/2019");
        assertEquals(article0.urlImage(),"top urlimg0");
        assertEquals(article0.urlArticle(),"top url0");

        Article article1 = Articles.get(1);
        assertEquals(article1.summary(),"top title1");
        assertEquals(article1.topics(),"top section1 > top itype1");
        assertEquals(article1.publishedDate(),"11/12/2019");
        assertEquals(article1.urlImage(),UNDEFINED);
        assertEquals(article1.urlArticle(),UNDEFINED);

        Article article2 = Articles.get(2);
        assertEquals(article2.summary(),UNDEFINED);
        assertEquals(article2.topics(),"top section2");
        assertEquals(article2.publishedDate(),"11/12/2019");
        assertEquals(article2.urlImage(),"top default url2");

        Article article3 = Articles.get(3);
        assertEquals(article3.topics(),"top subsection3 > top itype3");
        assertEquals(article3.publishedDate(),UNDEFINED);

        assertEquals(Articles.get(4).topics(),"top itype4");
    }
/*
    @Test
    public void testRxJavaRetrofitMechanismWithMockService() {
        ArrayList<Article> searchListArticle = sSearchResults.listOfArticle();
        ArrayList<Article> popularListArticle = sPopularResults.listOfArticle();
        ArrayList<Article> topListArticle = sTopResults.listOfArticle();

        assertEquals("statusOK", sSearchResults.getStatus());
        assertEquals(20,topListArticle.size());

        String url = popularListArticle.get(0).urlArticle();
        assertEquals(NYT_HOME_URL, url);

        String title = topListArticle.get(4).topics();
        assertEquals("Culture > Theatre", title);

        String testString = searchListArticle.get(8).summary();
        assertEquals("SEARCH : 8", testString);

        testString = popularListArticle.get(5).summary();
        assertEquals("POPULAR : 5", testString);

        testString = topListArticle.get(9).summary();
        assertEquals("TOP : 9", testString);

        testString = searchListArticle.get(12).publishedDate();
        assertEquals("21/11/2002", testString);

        testString = popularListArticle.get(17).urlImage();
        assertEquals("https://www.picasso.fr", testString);
    }
*/
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
      }
}