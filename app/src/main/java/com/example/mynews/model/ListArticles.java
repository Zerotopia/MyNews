package com.example.mynews.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * POJO class to get the list of the articles return by the NYT API.
 */
public class ListArticles {
    @SerializedName("docs")
    private ArrayList<Article> mDocs;

    public ArrayList<Article> getDocs() {
        return mDocs;
    }
}
