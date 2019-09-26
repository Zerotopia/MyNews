package com.example.mynews.model;

import java.util.ArrayList;

import com.example.mynews.model.Article;

public class Data {

    private ArrayList<Article> docs;

    public Data(ArrayList<Article> docs) {
        this.docs = docs;
    }



    public ArrayList<Article> getDocs() {
        return docs;
    }

    //public void setDocs(ArrayList<Article> docs) {
    //    this.docs = docs;
    //}
}
