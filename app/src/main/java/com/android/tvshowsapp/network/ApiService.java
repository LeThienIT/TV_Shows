package com.android.tvshowsapp.network;

import com.android.tvshowsapp.models.TVShowDetail;
import com.android.tvshowsapp.reponses.TVShowDetailsReponses;
import com.android.tvshowsapp.reponses.TVShowReponses;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    // https://www.episodate.com/api/most-popular?page=1
    @GET("most-popular")
    Call<TVShowReponses> getMostPopularTVShows(@Query("page") int page);

    //https://www.episodate.com/api/show-details?q=29560
    @GET("show-details")
    Call<TVShowDetailsReponses> getTvShowDetails(@Query("q") String tvShowId);
}
