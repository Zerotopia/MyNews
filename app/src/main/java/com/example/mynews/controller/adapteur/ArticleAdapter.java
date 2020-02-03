package com.example.mynews.controller.adapteur;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.mynews.R;
import com.example.mynews.controller.activity.WebActivity;
import com.example.mynews.model.Article;

import java.util.ArrayList;

import static com.example.mynews.model.Article.UNDEFINED;

/**
 * ArticleAdapter is the adapter for the RecyclerView that display the list of the articles
 * returns by the NYT API.
 */
public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ArticlesViewHolder> {

    private ArrayList<Article> mArticles;

    public ArticleAdapter(ArrayList<Article> articles) {
        mArticles = articles;
    }

    public static class ArticlesViewHolder extends RecyclerView.ViewHolder {

        public static final String URL_ARTICLE = "URLARTICLE";

        private TextView sTopicsArticle;
        private TextView sSummaryArticle;
        private TextView sDateArticle;
        private ImageView sImageArticle;
        private String sArticleUrl;

        public ArticlesViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            bindView();
            setOnArticleClickListener(context);
        }

        private void bindView() {
            sTopicsArticle = itemView.findViewById(R.id.row_article_topics);
            sSummaryArticle = itemView.findViewById(R.id.row_article_summary);
            sImageArticle = itemView.findViewById(R.id.row_article_image);
            sDateArticle = itemView.findViewById(R.id.row_article_date);
        }

        /**
         * When the user click on an article, we open a new activity that display
         * the content of the article in a WebView.
         * The case where the url is not valid is treat in the WebActivity.
         * @param context to launch the WebActivity.
         */
        private void setOnArticleClickListener(Context context) {
            itemView.setOnClickListener(view -> {
                Intent intent = new Intent(context, WebActivity.class);
                intent.putExtra(URL_ARTICLE, sArticleUrl);
                context.startActivity(intent);
            });
        }
    }

    @NonNull
    @Override
    public ArticlesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.row_article, viewGroup, false);
        return new ArticlesViewHolder(view, viewGroup.getContext());
    }

    @Override
    public void onBindViewHolder(@NonNull ArticlesViewHolder articlesViewHolder, int i) {
        Article article = mArticles.get(i);
        articlesViewHolder.sArticleUrl = article.urlArticle();
        setSafeText(articlesViewHolder.sTopicsArticle, article.topics());
        setSafeText(articlesViewHolder.sSummaryArticle, article.summary());
        setSafeText(articlesViewHolder.sDateArticle, article.publishedDate());
        loadImage(articlesViewHolder.itemView, articlesViewHolder.sImageArticle, article.urlImage());
    }

    @Override
    public int getItemCount() {
        return mArticles.size();
    }

    /**
     * This function is used to process the case where the parameter text haste value UNDEFINED.
     * (see Article class of the model).
     * In this case we display the resource string R.string.undefined.
     */
    private void setSafeText(TextView textView, String text) {
        if (text.equals(UNDEFINED)) {
            textView.setTypeface(null, Typeface.ITALIC);
            textView.setText(R.string.undefined);
        } else textView.setText(text);
    }

    /**
     * Function use to load the image of the article.
     * If no image are found we load by default the logo
     * of the application.
     */
    private void loadImage(View view, ImageView imageView, String urlImage) {
        RequestOptions options = new RequestOptions().centerCrop()
                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.mipmap.ic_launcher_round)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH);
        Glide.with(view)
                .load(urlImage)
                .apply(options)
                .into(imageView);
    }
}