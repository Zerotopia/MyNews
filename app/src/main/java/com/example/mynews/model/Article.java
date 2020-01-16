package com.example.mynews.model;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;
import com.google.gson.internal.LinkedTreeMap;

import java.util.ArrayList;

/**
 * POJO class to parse the informations about articles return by the NewYork Times API's.
 * Since Json answers return by different networks requests of NewYork Times API's are
 * different structure, We checked which variables are "filled" to return a good value.
 * MoreOver, some expected values are empty, so we checked if we can find same kind of
 * information in other variable of Json tree.
 */
public class Article {

    public static final String UNDEFINED = "undefined";
    public static final String NYT_HOME_URL = "https://www.nytimes.com/";

    /**
     * Variables used to determine the summary of an Article.
     */
    @SerializedName("snippet")
    private String mSnippet;
    @SerializedName("abstract")
    private String mAbstract;
    @SerializedName("lead_paragraph")
    private String mLeadPar;
    @SerializedName("headline")
    private ArticleTitle mHeadline;
    @SerializedName("title")
    private String mTitle;

    /**
     * Variables used to determine topics of an Article
     */
    @SerializedName("section")
    private String mSection;
    @SerializedName("subsection")
    private String mSubsection;
    @SerializedName("section_name")
    private String mSectionName;
    @SerializedName("subsection_name")
    private String mSubSectionName;
    @SerializedName("news_desk")
    private String mNewsDesk;
    @SerializedName("type")
    private String mType;
    @SerializedName("item_type")
    private String mItemType;
    @SerializedName("type_of_material")
    private String mTypeOfMaterial;
    @SerializedName("document_type")
    private String mDocType;

    /**
     * Variables used to determine the published date of an Article
     */
    @SerializedName("published_date")
    private String mPublishedDate;
    @SerializedName("created_date")
    private String mCreatedDate;
    @SerializedName("updated_date")
    private String mUpdatedDate;
    @SerializedName("pub_date")
    private String mPubDate;

    /**
     * Variables used to determine the url of a picture of an Article
     */
    @SerializedName("multimedia")
    private ArrayList<DataPicture> mMultimedia;
    @SerializedName("media")
    private Object mMedia;

    /**
     * Variables used to determine the url of an Article
     */
    @SerializedName("web_url")
    private String mWebUrl;
    @SerializedName("url")
    private String mUrl;

    /**
     * The constructor is used to construct an answer in Mock service.
     */
    public Article(String snippet, String section, String subsection, String pubDate, ArrayList<DataPicture> multimedia, String url) {
        mSnippet = snippet;
        mSection = section;
        mSubsection = subsection;
        mPubDate = pubDate;
        mMultimedia = multimedia;
        mUrl = url;
    }

    /**
     * @return a summary of an article, and UNDEFINED if
     * there is no relevant information in Json tree.
     */
    public String summary() {
        if (existString(mSnippet)) return mSnippet;
        else if (existString(mAbstract)) return mAbstract;
        else if (existString(mLeadPar)) return mLeadPar;
        else if (mHeadline != null) {
            String main = mHeadline.getMain();
            if (existString(main)) return main;
            else return UNDEFINED;
        } else if (existString(mTitle)) return mTitle;
        else return UNDEFINED;
    }

    /**
     * In general case topics is principally "section > subsection"
     * But sometimes this information are empty so we will search information
     * in news desk or type of document.
     *
     * @return some information about topics or document type of an article.
     * If there is a topic and a subtopic, then the result is "topic > subtopic"
     * else if there is only a topic then the result is "topic"
     * else the result is UNDEFINED.
     */
    public String topics() {
        String topic = UNDEFINED;
        String subtopic = "";

        if (existString(mSection)) {
            topic = mSection;
            if (existString(mSubsection)) subtopic = mSubsection;
            else if (existString(mType)) subtopic = mType;
            else if (existString(mItemType)) subtopic = mItemType;
        } else if (existString(mSubsection)) {
            topic = mSubsection;
            if (existString(mType)) subtopic = mType;
            else if (existString(mItemType)) subtopic = mItemType;
        } else if (existString(mType)) topic = mType;
        else if (existString(mItemType)) topic = mItemType;
        else if (existString(mSectionName)) {
            topic = mSectionName;
            if (existString(mSubSectionName)) subtopic = mSubSectionName;
            else if (existString(mNewsDesk)) subtopic = mNewsDesk;
            else if (existString(mTypeOfMaterial)) subtopic = mTypeOfMaterial;
            else if (existString(mDocType)) subtopic = mDocType;
        } else if (existString(mSubSectionName)) {
            topic = mSubSectionName;
            if (existString(mNewsDesk)) subtopic = mNewsDesk;
            else if (existString(mTypeOfMaterial)) subtopic = mTypeOfMaterial;
            else if (existString(mDocType)) subtopic = mDocType;
        } else if (existString(mNewsDesk)) {
            topic = mNewsDesk;
            if (existString(mTypeOfMaterial)) subtopic = mTypeOfMaterial;
            else if (existString(mDocType)) subtopic = mDocType;
        } else if (existString(mTypeOfMaterial)) topic = mTypeOfMaterial;
        else if (existString(mDocType)) topic = mDocType;

        if ((topic.equalsIgnoreCase(subtopic)) || (subtopic.isEmpty()))
            return topic;
        else return topic + " > " + subtopic;
    }

    /**
     * NewYork Times API's return a date in format "yyyy-mm-dd+time"
     * were "+time" is hour, minute ...
     *
     * @return the published date of an Article in format dd/mm/yyyy,
     * and return UNDEFINED if no date are found.
     */
    public String publishedDate() {
        String nyDate = "";

        if (existString(mPubDate)) nyDate = mPubDate;
        else if (existString(mPublishedDate)) nyDate = mPublishedDate;
        else if (existString(mCreatedDate)) nyDate = mCreatedDate;
        else if (existString(mUpdatedDate)) nyDate = mUpdatedDate;

        if (!nyDate.isEmpty()) {
            String year = nyDate.substring(0, 4);
            String month = nyDate.substring(5, 7);
            String day = nyDate.substring(8, 10);
            return day + "/" + month + "/" + year;
        } else return UNDEFINED;
    }

    /**
     * In Most Popular API, the Array of picture is in an array call "media"
     * But if media is empty then its value is the empty string ""
     * To solve this type Problem we use the function "instanccof" to determine
     * in which case we are.
     *
     * @return the url of the picture of size 75 x 75 if there exists, return
     * the url of the first picture of the array of pictures otherwise,
     * and return UNDEFINED if there is no picture.
     */
    public String urlImage() {
        ArrayList<DataPicture> arrayPicture;
        LinkedTreeMap treeMediaData;
        MetaDataPicture metaData;
        if (existArray(mMultimedia)) arrayPicture = mMultimedia;
        else if (mMedia instanceof ArrayList) {
            treeMediaData = (LinkedTreeMap) ((ArrayList) mMedia).get(0);
            Gson gson = new Gson();
            JsonObject json = gson.toJsonTree(treeMediaData).getAsJsonObject();
            metaData = gson.fromJson(json, MetaDataPicture.class);
            arrayPicture = metaData.getMediaMetaData();
            if (!existArray(arrayPicture)) return UNDEFINED;
        } else return UNDEFINED;
        DataPicture currentPicture = arrayPicture.get(0);
        boolean goodSize = false;
        int pictureIndex = 0;

        while (!goodSize && pictureIndex < arrayPicture.size()) {
            currentPicture = arrayPicture.get(pictureIndex);
            goodSize = (currentPicture.getHeight() == 75 && currentPicture.getWidth() == 75);
            pictureIndex++;
        }

        // url from Article Search API are not complete so we should
        // complete url with a prefix to have a valid url.
        // mWebUrl is a variable that be filled only by Json return
        // by the Article Search API.
        String prefixUrl = (mWebUrl != null) ? NYT_HOME_URL : "";
        String postUrl = (goodSize) ? currentPicture.getUrl() : arrayPicture.get(0).getUrl();
        if (existString(postUrl)) return prefixUrl + postUrl;
        else return UNDEFINED;
    }

    /**
     *
     * @return the url of an Article.
     * Theoretically UNDEFINED should be never return, but almost UNDEFINED
     * in this file are here to avoid an eventually NullPointerException.
     */
    public String urlArticle() {
        if (existString(mWebUrl)) return mWebUrl;
        else if (existString(mUrl)) return mUrl;
        else return UNDEFINED;
    }

    /**
     * These two functions are used to have a more readable code.
     * If a value is not null that means it has been instanced
     * by the "GSON processus". And we check moreover if the
     * instanced value are not empty.
     */
    private boolean existString(String string) {
        return ((string != null) && !string.isEmpty());
    }

    private <T> boolean existArray(ArrayList<T> array) {
        return ((array != null) && (array.size() != 0));
    }
}
