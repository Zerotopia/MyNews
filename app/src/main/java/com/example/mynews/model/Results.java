package com.example.mynews.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Results {

    private String status;

    private Data response;
    @SerializedName("results")
    private ArrayList<Article> reponse2;



    public Results(String status, Data response, ArrayList<Article> reponse2) {
        this.status = status;
        this.response = response;
        this.reponse2 = reponse2;
    }

    public ArrayList<Article> listArticle() {
        if (response == null) return reponse2;
        else return response.getDocs();
    }

   // public void setReponse2(ArrayList<Article> reponse2) {
   //     this.reponse2 = reponse2;
   // }

    public String getStatus() {
        return status;
    }

    //public void setStatus(String status) {
    //    this.status = status;
    // }

    // public Data getResponse() {
    //    return response;
    // }

    // public void setResponse(Data response) {
    //    this.response = response;
    // }
}
