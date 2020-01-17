package com.example.mynews;


import com.example.mynews.model.Article;
import com.example.mynews.network.NYService;
import com.example.mynews.model.Results;
import com.example.mynews.network.RetrofitClient;
import com.google.gson.Gson;

import org.junit.BeforeClass;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.example.mynews.model.Article.NYT_HOME_URL;
import static com.example.mynews.model.Article.UNDEFINED;
import static com.example.mynews.model.FormatMaker.d8DateFormat;
import static com.example.mynews.model.FormatMaker.filterQueryFormat;
import static com.example.mynews.model.FormatMaker.stringDateToMillis;
import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
//@RunWith(MockitoJUnitRunner.class)
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

    private static Set<String> sTopics;

    private static void rxJavaCall(Observable<Results> observable, final int api) {

        observable.subscribeOn(Schedulers.trampoline())
                .observeOn(Schedulers.trampoline())
                .subscribe(new Observer<Results>() {

                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(Results results) {
                        if (api == SEARCH) sSearchResults = results;
                        else if (api == MOSTPOPULAR) sPopularResults = results;
                        else if (api == TOPARTICLE) sTopResults = results;
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    @BeforeClass
    public static void setUp() throws FileNotFoundException {
        sTopics = new HashSet<>();

        // set up variables to test the parsing Json files from POJO class
        Gson gson = new Gson();
        sSearchJson = gson.fromJson(new FileReader("search.json"), Results.class);
        sPopularJson = gson.fromJson(new FileReader("mostpopular.json"), Results.class);
        sTopJson = gson.fromJson(new FileReader("topstories.json"), Results.class);

        // set up variables to test retrofit client.
        NYService nyService = RetrofitClient.getMock();

        Observable<Results> searchObs = nyService.searchArticle(
                "math",
                "20001002",
                "20100101",
                "",
                NYService.APIKEY);
        Observable<Results> popularObs = nyService.popularArticle(NYService.APIKEY);
        Observable<Results> topObs = nyService.topArticle("home", NYService.APIKEY);

        rxJavaCall(searchObs, SEARCH);
        rxJavaCall(popularObs, MOSTPOPULAR);
        rxJavaCall(topObs, TOPARTICLE);
    }

    @Test
    public void stringDateToMillis_isCorrect() {
        assertEquals(86400000, stringDateToMillis("02/01/1970"));
    }

    @Test
    public void d8DateFormat_isCorrect() {
        assertEquals("19950723", d8DateFormat("23/07/1995"));
        assertEquals("", d8DateFormat(""));
    }

    @Test
    public void filterQueryFormat_isCorrect() {
        assertEquals("news_desk:()", filterQueryFormat(sTopics));
        sTopics.add("Defgh");
        assertEquals("news_desk:(\"Defgh\")", filterQueryFormat(sTopics));
        sTopics.add("Ijkl");
        sTopics.add("Stu");
        assertEquals("news_desk:(\"Ijkl\" \"Stu\" \"Defgh\")", filterQueryFormat(sTopics));
    }

    @Test
    public void checkResultsModelForSearchJson() {
        assertEquals("OK", sSearchJson.getStatus());

        ArrayList<Article> Articles = sSearchJson.listOfArticle();

        Article article0 = Articles.get(0);
        assertEquals("search snippet0", article0.summary());
        assertEquals("search sectionname0 > search subsectionname0", article0.topics());
        assertEquals("01/08/2014", article0.publishedDate());
        assertEquals(NYT_HOME_URL + "search urlimg0", article0.urlImage());
        assertEquals("search url0", article0.urlArticle());

        Article article1 = Articles.get(1);
        assertEquals("search abstract1", article1.summary());
        assertEquals("search sectionname1 > search newsdesk1", article1.topics());
        assertEquals(UNDEFINED, article1.publishedDate());
        assertEquals(UNDEFINED, article1.urlImage());
        assertEquals(UNDEFINED, article1.urlArticle());

        Article article2 = Articles.get(2);
        assertEquals("search lpar2", article2.summary());
        assertEquals("search sectionname2 > search tom2", article2.topics());
        assertEquals("17/07/2019", article2.publishedDate());
        assertEquals(NYT_HOME_URL + "search default url2", article2.urlImage());


        Article article3 = Articles.get(3);
        assertEquals("search main3", article3.summary());
        assertEquals("search sectionname3 > search dtype3", article3.topics());
        assertEquals(UNDEFINED, article3.urlImage());

        Article article4 = Articles.get(4);
        assertEquals(UNDEFINED, article4.summary());
        assertEquals("search subsectionname4 > search newsdesk4", article4.topics());

        assertEquals("search subsectionname5 > search tom5", Articles.get(5).topics());
        assertEquals("search subsectionname6 > search dtype6", Articles.get(6).topics());
        assertEquals("search newsdesk7 > search tom7", Articles.get(7).topics());
        assertEquals("search newsdesk8 > search dtype8", Articles.get(8).topics());
        assertEquals("search tom9", Articles.get(9).topics());
    }

    @Test
    public void checkResultsModelForMostPopularJson() {
        assertEquals("OK", sPopularJson.getStatus());

        ArrayList<Article> Articles = sPopularJson.listOfArticle();

        Article article0 = Articles.get(0);
        assertEquals("most abstract0", article0.summary());
        assertEquals("most section0 > most subsection0", article0.topics());
        assertEquals("10/12/2019", article0.publishedDate());
        assertEquals("most urlimg0", article0.urlImage());
        assertEquals("most url0", article0.urlArticle());

        Article article1 = Articles.get(1);
        assertEquals("most title1", article1.summary());
        assertEquals("most section1 > most type1", article1.topics());
        assertEquals(UNDEFINED, article1.publishedDate());
        assertEquals("most default url1", article1.urlImage());
        assertEquals(UNDEFINED, article1.urlArticle());

        Article article2 = Articles.get(2);
        assertEquals(UNDEFINED, article2.summary());
        assertEquals("most subsection2 > most type2", article2.topics());
        assertEquals("16/12/2019", article2.publishedDate());
        assertEquals(UNDEFINED, article2.urlImage());

        Article article3 = Articles.get(3);
        assertEquals("most section3", article3.topics());
        assertEquals("most default url3", article3.urlImage());

        Article article4 = Articles.get(4);
        assertEquals("most type4", article4.topics());
        assertEquals(UNDEFINED, article4.urlImage());

        assertEquals(UNDEFINED, Articles.get(5).topics());
    }

    @Test
    public void checkResultsModelForTopStoriesJson() {
        assertEquals("OK", sTopJson.getStatus());

        ArrayList<Article> Articles = sTopJson.listOfArticle();

        Article article0 = Articles.get(0);
        assertEquals("top abstract0", article0.summary());
        assertEquals("top section0 > top subsection0", article0.topics());
        assertEquals("11/12/2019", article0.publishedDate());
        assertEquals("top urlimg0", article0.urlImage());
        assertEquals("top url0", article0.urlArticle());

        Article article1 = Articles.get(1);
        assertEquals("top title1", article1.summary());
        assertEquals("top section1 > top itype1", article1.topics());
        assertEquals("11/12/2019", article1.publishedDate());
        assertEquals(UNDEFINED, article1.urlImage());
        assertEquals(UNDEFINED, article1.urlArticle());

        Article article2 = Articles.get(2);
        assertEquals(UNDEFINED, article2.summary());
        assertEquals("top section2", article2.topics());
        assertEquals("11/12/2019", article2.publishedDate());
        assertEquals("top default url2", article2.urlImage());

        Article article3 = Articles.get(3);
        assertEquals("top subsection3 > top itype3", article3.topics());
        assertEquals(UNDEFINED, article3.publishedDate());

        assertEquals("top itype4", Articles.get(4).topics());
    }

    @Test
    public void testRxJavaRetrofitMechanismWithMockService() {
        ArrayList<Article> searchListArticle = sSearchResults.listOfArticle();
        ArrayList<Article> popularListArticle = sPopularResults.listOfArticle();
        ArrayList<Article> topListArticle = sTopResults.listOfArticle();

        assertEquals("statusOK", sSearchResults.getStatus());
        assertEquals(20, topListArticle.size());

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


    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }
}