package com.news.app;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {

    MutableLiveData<NewsDataModel> articleData = new MutableLiveData<>();

    public void putArticleLIst(NewsDataModel article ) {
        articleData.setValue(article);
    }
}