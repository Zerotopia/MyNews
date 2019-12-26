package com.example.mynews;

import com.example.mynews.model.Article;
import com.example.mynews.model.Results;
import com.google.gson.Gson;

import org.junit.BeforeClass;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

import static com.example.mynews.model.Article.UNDEFINED;
import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    private static Results sSearchResults;
    private static Results sPopularResults;
    private static Results sTopResults;

    @BeforeClass
    public static void setUp() throws FileNotFoundException {
        Gson gson = new Gson();
        sSearchResults = gson.fromJson(new FileReader("search.json"),Results.class);
        sPopularResults = gson.fromJson(new FileReader("mostpopular.json"),Results.class);
        sTopResults = gson.fromJson(new FileReader("topstories.json"),Results.class);
    }
/*
    @Test
    public void checkResultsModelForSearchJson(){
        assertEquals(sSearchResults.getStatus(),"OK");

        ArrayList<Article> Articles = sSearchResults.listOfArticle();

        Article article0 = Articles.get(0);
        assertEquals(article0.summary(),"search snippet0");
        assertEquals(article0.topics(),"search sectionname0 > search subsectionname0");
        assertEquals(article0.publishedDate(),"01/08/2014");
        assertEquals(article0.urlImage(),"search urlimg0");
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
        assertEquals(article1.publishedDate(),"17/07/2019");
        assertEquals(article1.urlImage(),UNDEFINED);
        assertEquals(article1.urlArticle(),UNDEFINED);

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
    public void checkResultsModelForMostpopularJson(){
        assertEquals(sPopularResults.getStatus(),"OK");

        ArrayList<Article> Articles = sPopularResults.listOfArticle();

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
        assertEquals(article1.urlImage(),UNDEFINED);
        assertEquals(article1.urlArticle(),UNDEFINED);

        Article article2 = Articles.get(2);
        assertEquals(article2.summary(),UNDEFINED);
        assertEquals(article2.topics(),"most subsection2 > most type2");
        assertEquals(article1.publishedDate(),"16/12/2019");
        assertEquals(article1.urlImage(),UNDEFINED);

        Article article3 = Articles.get(3);
        assertEquals(article3.topics(),"most section3");
        assertEquals(article3.urlImage(),UNDEFINED);

        Article article4 = Articles.get(4);
        assertEquals(article4.topics(),"most type4");
        assertEquals(article3.urlImage(),UNDEFINED);

        assertEquals(Articles.get(5).topics(),UNDEFINED);
    }

    @Test
    public void checkResultsModelForTopstoriesJson(){
        assertEquals(sTopResults.getStatus(),"OK");

        ArrayList<Article> Articles = sTopResults.listOfArticle();

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
        assertEquals(article1.publishedDate(),"11/12/2019");
        assertEquals(article1.urlImage(),UNDEFINED);

        Article article3 = Articles.get(3);
        assertEquals(article3.topics(),"top subsection3 > top itype3");
        assertEquals(article3.publishedDate(),UNDEFINED);

        assertEquals(Articles.get(4).topics(),UNDEFINED);
    }
*/
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
      }
}