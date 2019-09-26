package com.example.mynews.controller;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.mynews.R;
import com.example.mynews.model.Article;

import java.util.ArrayList;

public class ArticlesAdapter extends RecyclerView.Adapter<ArticlesAdapter.ArticlesViewHolder> {

    private ArrayList<Article> mArticles;

    public ArticlesAdapter(ArrayList<Article> articles) {
        mArticles = articles;
    }

    public static class ArticlesViewHolder extends RecyclerView.ViewHolder {

        TextView titleArticle;
        TextView resumeArticle;
        TextView dateArticle;
        ImageView imageArticle;
        Context mContext;
        String articleUrl;


        public ArticlesViewHolder(@NonNull View itemView, Context context) {

            super(itemView);
            titleArticle = itemView.findViewById(R.id.title_art);
            resumeArticle = itemView.findViewById(R.id.resume);
            imageArticle = itemView.findViewById(R.id.article_image);
            dateArticle = itemView.findViewById(R.id.date);
            mContext = context;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, WebActivity.class);
                    intent.putExtra("url", articleUrl);
                    mContext.startActivity(intent);

                }
            });
        }

        public void setArticleUrl(String articleUrl) {
            this.articleUrl = articleUrl;
        }
    }

    @NonNull
    @Override
    public ArticlesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.article, viewGroup, false);
        return new ArticlesViewHolder(view, viewGroup.getContext());
    }

    @Override
    public void onBindViewHolder(@NonNull ArticlesViewHolder articlesViewHolder, int i) {
        Article article = mArticles.get(i);
        articlesViewHolder.setArticleUrl(article.urlArticle());
        articlesViewHolder.titleArticle.setText(article.topicArticle());
        articlesViewHolder.resumeArticle.setText(article.resumeArticle());
        articlesViewHolder.dateArticle.setText(article.dateArtice());
        if (article.urlImage().equals("undefined")) {
            Glide.with(articlesViewHolder.itemView)
                    .load(R.mipmap.ic_launcher_round)
                    .into(articlesViewHolder.imageArticle);
        } else {
            Glide.with(articlesViewHolder.itemView)
                    .load(article.urlImage())
                    .into(articlesViewHolder.imageArticle);
        }

//        RequestOptions options = new RequestOptions().centerCrop()
//                .placeholder(R.drawable.default_avatar)
//                .error(R.drawable.default_avatar)
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .priority(Priority.HIGH);
//        Glide.with(mContext)
//                .load(imgUrl)
//                .apply(options)
//                .into(picThumbnail);
    }

    @Override
    public int getItemCount() {
        return mArticles.size();
    }


}
