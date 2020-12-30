package com.android.tvshowsapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.android.tvshowsapp.database.TVShowDataBase;
import com.android.tvshowsapp.models.TVShow;

import java.util.List;

import io.reactivex.Flowable;

public class  WatchListViewModel extends AndroidViewModel {

    private TVShowDataBase tvShowDataBase;

    private WatchListViewModel(@NonNull Application application){
        super(application);
        tvShowDataBase = TVShowDataBase.getTvShowsDatabase(application);

    }

    public Flowable<List<TVShow>> loadWatchList(){
        return tvShowDataBase.tvShowDao().getWatchList();
    }
}
