package com.example.mynews.model;

import com.google.gson.annotations.SerializedName;

/**
 * POJO class to get information about the head picture of an article.
 */
public class DataPicture {

    @SerializedName("height")
    private int mHeight;
    @SerializedName("width")
    private int mWidth;

    public DataPicture(String url) {
        mUrl = url;
    }

    @SerializedName("url")
    private String mUrl;

    public int getHeight() {
        return mHeight;
    }

    public int getWidth() {
        return mWidth;
    }

    public String getUrl() {
        return mUrl;
    }
}
