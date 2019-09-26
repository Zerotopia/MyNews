package com.example.mynews.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

class MetaDataPicture implements Serializable {

    @SerializedName("media-metadata")
    private ArrayList<DataPicture> mediaData;

    public MetaDataPicture(ArrayList<DataPicture> mediaData) {
        this.mediaData = mediaData;
    }

    public ArrayList<DataPicture> getMediaData() {
        return mediaData;
    }
}
