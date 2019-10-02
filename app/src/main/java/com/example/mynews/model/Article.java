package com.example.mynews.model;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;
import com.google.gson.internal.LinkedTreeMap;

import java.util.ArrayList;

public class Article {

    private String url;
    @SerializedName("web_url")
    private String webUrl;

    private String snippet;
    @SerializedName("abstract")
    private String resume;
    @SerializedName("lead_paragraph")
    private String lpar;

    private String section;
    private String subsection;
    @SerializedName("news_desk")
    private String desk;
    @SerializedName("section_name")
    private String sectionName;
    @SerializedName("subsection_name")
    private String subsectionName;
    @SerializedName("item_type")
    private String type;
    @SerializedName("type_of_material")
    private String docType;


    @SerializedName("pub_date")
    private String pubDate;
    @SerializedName("published_date")
    private String date;

    @SerializedName("media")
    private Object media;
    private ArrayList<DataPicture> multimedia;

    public Article(String url, String snippet, String section, String subsection, String date, ArrayList<DataPicture> multimedia) {
        this.url = url;
        this.snippet = snippet;
        this.section = section;
        this.subsection = subsection;
        this.date = date;
        this.multimedia = multimedia;
    }

//    public Article() {
//        Log.d("CONSTRUCTEUR", "Article: passage constructeur");
//        Log.d("CONSTRUCTEUR", "Article: " + ((multimedia != null) ? multimedia.size(): "NULLL" ));
//
//    }

    //traitement des informations dans le code get.


    public String urlArticle() {
        if (existString(webUrl)) return webUrl;
        else if (existString(url)) return url;
        else return "https://www.nytimes.com/";
    }

    public String resumeArticle() {
        if (existString(snippet)) return snippet;
        else if (existString(resume)) return resume;
        else if (existString(lpar)) return lpar;
        else return "undefined";
    }

    public String topicArticle() {
        String topic;
        String subtopic;

        if (existString(section)) topic = section;
        else if (existString(sectionName)) topic = sectionName;
        else if (existString(desk)) topic = desk;
        else if (existString(type)) topic = type;
        else if (existString(docType)) topic = docType;
        else topic = "undefied";

        if (existString(subsection)) subtopic = subsection;
        else if (existString(subsectionName)) subtopic = subsectionName;
        else if (existString(desk)) subtopic = desk;
        else subtopic = "";

        if ((topic.equals(subtopic)) || subtopic.isEmpty()) return topic;
        else return topic + " > " + subtopic;
    }

    public String dateArtice() {
        String nyDate;

        if (existString(pubDate)) nyDate = pubDate;
        else if (existString(date)) nyDate = date;
        else nyDate = "";

        if (!nyDate.isEmpty()) {
            String year = nyDate.substring(0, 4);
            String month = nyDate.substring(5, 7);
            String day = nyDate.substring(8, 10);

            return day + "/" + month + "/" + year;
        } else return "undefined";

    }

    public String urlImage() {
        LinkedTreeMap trueMedia;
        MetaDataPicture meta;
        ArrayList<DataPicture> arrayPicture;

        if (existArray(multimedia)) arrayPicture = multimedia;
        else if (media instanceof ArrayList) {
            trueMedia = (LinkedTreeMap) ((ArrayList) media).get(0);
            Gson gson = new Gson();
            JsonObject jo = gson.toJsonTree(trueMedia).getAsJsonObject();
            meta = gson.fromJson(jo, MetaDataPicture.class);
            if (existArray(meta.getMediaData())) arrayPicture = meta.getMediaData();
            else return "undefined";
        } else return "undefined";

        int i = 0;
        DataPicture picture; //= arrayPicture.get(i);
        boolean goodSize = false; // ((picture.getHeight() == 75) && (picture.getWidth() == 75));

        while (!goodSize && i < arrayPicture.size()) {
            picture = arrayPicture.get(i);
            goodSize = ((picture.getHeight() == 75) && (picture.getWidth() == 75));
            i++;
        }

        String prefixUrl = (existString(webUrl)) ? "https://www.nytimes.com/" : "";

        if (goodSize) return prefixUrl + arrayPicture.get(0).getUrl();
        else return prefixUrl + multimedia.get(0).getUrl();
    }


    //ArrayList<DataPicture> arrayPicture;

    //if (existArray(media)) arr {

    //}

//        if ((multimedia != null) && (multimedia.size() != 0)) {
//            int i = 0;
//            DataPicture picture = multimedia.get(i);
//            boolean goodSize = ((picture.getHeight() == 75) && (picture.getWidth() == 75));
//
//            while (!goodSize && i < multimedia.size()) {
//                i++;
//                picture = multimedia.get(i);
//                goodSize = ((picture.getHeight() == 75) && (picture.getWidth() == 75));
//            }
//
//            if (goodSize) return prefixUrl + picture.getUrl();
//            else return prefixUrl + multimedia.get(0).getUrl();
//        } else return "undefined";
//    }

    private boolean existString(String string) {
        return ((string != null) && !string.isEmpty());
    }

    private <T> boolean existArray(ArrayList<T> array) {
        return ((array != null) && (array.size() != 0));
    }
}
