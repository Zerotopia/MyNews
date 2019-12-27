package com.example.mynews.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ListArticles {
    @SerializedName("docs")
    private ArrayList<Article> mDocs;

    public ArrayList<Article> getDocs() {
        return mDocs;
    }


}
