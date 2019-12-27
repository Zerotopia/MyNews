package com.example.mynews.model;

import com.google.gson.annotations.SerializedName;

public class ArticleTitle {

    @SerializedName("main")
    private String mMain;

    public String getMain() {
        return mMain;
    }
}
