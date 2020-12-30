package com.android.tvshowsapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.android.tvshowsapp.database.TVShowDataBase;
import com.android.tvshowsapp.models.TVShow;
import com.android.tvshowsapp.reponses.TVShowDetailsReponses;
import com.android.tvshowsapp.repositories.TVShowDetailsRepository;

import io.reactivex.Completable;

public class TVShowDetailsViewModel extends AndroidViewModel {

    private TVShowDetailsRepository tvShowDetailsRepository;
    private TVShowDataBase tvShowDataBase;

    public TVShowDetailsViewModel(@NonNull Application application){
        super(application);
        tvShowDetailsRepository = new TVShowDetailsRepository();
        tvShowDataBase = TVShowDataBase.getTvShowsDatabase(application);
    }

    public LiveData<TVShowDetailsReponses> getTVShowDetails(String tvShowId){
        return tvShowDetailsRepository.getTvShowDetails(tvShowId);
    }

    public Completable addToWatchList(TVShow tvShow){
        return tvShowDataBase.tvShowDao().addToWatchList(tvShow);
    }

}
