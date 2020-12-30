package com.android.tvshowsapp.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.android.tvshowsapp.dao.TVShowDao;
import com.android.tvshowsapp.models.TVShow;
import com.android.tvshowsapp.models.TVShowDetail;

@Database(entities = TVShow.class, version = 1, exportSchema = false)
public abstract class TVShowDataBase extends RoomDatabase {

    private static TVShowDataBase tvShowsDatabase;

    public static synchronized TVShowDataBase getTvShowsDatabase(Context context){
        if(tvShowsDatabase == null){
            tvShowsDatabase = Room.databaseBuilder(context, TVShowDataBase.class, "tv show db").build();
        }
        return tvShowsDatabase;
    }

    public abstract TVShowDao tvShowDao();
}
