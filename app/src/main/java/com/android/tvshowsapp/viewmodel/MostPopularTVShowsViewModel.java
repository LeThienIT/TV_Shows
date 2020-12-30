package com.android.tvshowsapp.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.android.tvshowsapp.reponses.TVShowReponses;
import com.android.tvshowsapp.repositories.MostPopularTVShowsReponsitory;

public class MostPopularTVShowsViewModel extends ViewModel {

    private MostPopularTVShowsReponsitory mostPopularTVShowsReponsitory;

    public MostPopularTVShowsViewModel(){
        mostPopularTVShowsReponsitory = new MostPopularTVShowsReponsitory();
    }

    public LiveData<TVShowReponses> getMostPopularTVShows(int page){
        return mostPopularTVShowsReponsitory.getMostPopularTVShows(page);
    }
}
