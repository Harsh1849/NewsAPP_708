package com.news.app;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("everything")
    Call<NewsDataModel> getNewsList(@Query("q") String q,
//                                    @Query("from") String from,
                                    @Query("sortBy") String sortBy,
                                    @Query("apiKey") String apiKey
    );
}
