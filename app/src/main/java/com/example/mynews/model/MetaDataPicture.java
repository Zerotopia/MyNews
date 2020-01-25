package com.example.mynews.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * POJO class to parse an array of picture from the result of he Most Popular NYT API.
 */
public class MetaDataPicture {

    @SerializedName("media-metadata")
    private ArrayList<DataPicture> mMediaMetaData;

    public ArrayList<DataPicture> getMediaMetaData() {
        return mMediaMetaData;
    }
}
