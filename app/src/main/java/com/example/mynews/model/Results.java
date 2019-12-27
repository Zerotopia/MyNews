package com.example.mynews.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Results {

    public Results(String status, ArrayList<Article> results) {
        mStatus = status;
        mResults = results;
    }

    @SerializedName("status")
    private String mStatus;
    @SerializedName("results")
    private ArrayList<Article> mResults;
    @SerializedName("response")
    private ListArticles mResponse;

    public String getStatus() {
        return mStatus;
    }

    public ArrayList<Article> listOfArticle() {
        if (mResults != null) return mResults;
        else if (mResponse != null) return mResponse.getDocs();
        else return new ArrayList<>();
    }
}
