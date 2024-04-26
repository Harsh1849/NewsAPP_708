package com.news.app;

import static com.news.app.Extensions.NEWSDATA;
import static com.news.app.Extensions.loadImage;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.google.gson.Gson;

import java.util.ArrayList;

public class TopStoryAdapter extends RecyclerView.Adapter<TopStoryAdapter.ViewHolder> {

    private final Context context;
    private final ArrayList<Article> arrayList;
    private final NavController navController;
    private final NewsDataModel newsDataModel;
    private MainViewModel mainViewModel;

    public TopStoryAdapter(Context context, ArrayList<Article> arrayList, NavController navController, NewsDataModel newsDataModel, MainViewModel mainViewModel) {
        this.context = context;
        this.arrayList = arrayList;
        this.navController = navController;
        this.newsDataModel = newsDataModel;
        this.mainViewModel = mainViewModel;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_item_top_story, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        loadImage(holder.topNewsImageView, arrayList.get(position).getUrlToImage());
        holder.mainLayout.setOnClickListener(v -> {
            mainViewModel.putArticleLIst(newsDataModel);
            Bundle bundle = new Bundle();
            bundle.putString(NEWSDATA, new Gson().toJson(arrayList.get(position), Article.class));
//            bundle.putString(NEWSLIST, new Gson().toJson(newsDataModel, NewsDataModel.class));
            navController.navigate(R.id.action_nav_news_list_to_nav_news_detail, bundle);
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView topNewsImageView;
        MaterialCardView mainLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            mainLayout = itemView.findViewById(R.id.main_layout);
            topNewsImageView = itemView.findViewById(R.id.top_news_imageView);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}