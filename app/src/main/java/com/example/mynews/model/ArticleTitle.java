package com.example.mynews.model;

import com.google.gson.annotations.SerializedName;

/**
 * POJO class to get the title of an article.
 */
public class ArticleTitle {

    @SerializedName("main")
    private String mMain;

    public String getMain() {
        return mMain;
    }
}
