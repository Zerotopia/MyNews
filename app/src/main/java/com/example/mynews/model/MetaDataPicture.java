package com.example.mynews.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MetaDataPicture {

    @SerializedName("media-metadata")
    private ArrayList<DataPicture> mMediaMetaData;

    public ArrayList<DataPicture> getMediaMetaData() {
        return mMediaMetaData;
    }
}
