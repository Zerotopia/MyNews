package com.example.mynews.controller.adapteur;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.mynews.R;
import com.example.mynews.controller.activity.WebActivity;
import com.example.mynews.model.Article;

import java.util.ArrayList;

import static com.example.mynews.model.Article.NYT_HOME_URL;
import static com.example.mynews.model.Article.UNDEFINED;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ArticlesViewHolder> {

    private ArrayList<Article> mArticles;

    public ArticleAdapter(ArrayList<Article> articles) {
        mArticles = articles;
    }

    public static class ArticlesViewHolder extends RecyclerView.ViewHolder {

        TextView sTopicsArticle;
        TextView sSummaryArticle;
        TextView sDateArticle;
        ImageView sImageArticle;
        Context sContext;
        String sArticleUrl;


        public ArticlesViewHolder(@NonNull View itemView, Context context) {

            super(itemView);
            sTopicsArticle = itemView.findViewById(R.id.row_article_topics);
            sSummaryArticle = itemView.findViewById(R.id.row_article_summary);
            sImageArticle = itemView.findViewById(R.id.row_article_image);
            sDateArticle = itemView.findViewById(R.id.row_article_date);
            sContext = context;

            itemView.setOnClickListener(view -> {
                Intent intent = new Intent(sContext, WebActivity.class);
                intent.putExtra("url", sArticleUrl);
                sContext.startActivity(intent);
            });
        }

        private void setArticleUrl(String articleUrl) {
            sArticleUrl = articleUrl;
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
        articlesViewHolder.setArticleUrl(article.urlArticle());
        setSafeText(articlesViewHolder.sTopicsArticle, article.topics());
        setSafeText(articlesViewHolder.sSummaryArticle, article.summary());
        setSafeText(articlesViewHolder.sDateArticle, article.publishedDate());

        RequestOptions options = new RequestOptions().centerCrop()
                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.mipmap.ic_launcher_round)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH);
        Glide.with(articlesViewHolder.itemView)
                .load(article.urlImage())
                .apply(options)
                .into(articlesViewHolder.sImageArticle);
    }

    @Override
    public int getItemCount() {
        return mArticles.size();
    }

    private void setSafeText(TextView textView, String text) {
        if (text.equals(UNDEFINED)) {
            textView.setTypeface(null, Typeface.ITALIC);
            textView.setText(R.string.undefined);
        } else textView.setText(text);
    }
}


