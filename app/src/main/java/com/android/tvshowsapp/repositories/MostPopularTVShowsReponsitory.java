package com.android.tvshowsapp.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.android.tvshowsapp.network.ApiClient;
import com.android.tvshowsapp.network.ApiService;
import com.android.tvshowsapp.reponses.TVShowReponses;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MostPopularTVShowsReponsitory {
    private ApiService apiService;

    public MostPopularTVShowsReponsitory(){
        apiService = ApiClient.getRetrofit().create(ApiService.class);
    }

    public LiveData<TVShowReponses> getMostPopularTVShows (int page) {
        MutableLiveData<TVShowReponses> data = new MutableLiveData<>();
        apiService.getMostPopularTVShows(page).enqueue(new Callback<TVShowReponses>() {
            @Override
            public void onResponse(Call<TVShowReponses> call, Response<TVShowReponses> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<TVShowReponses> call, Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }
}
