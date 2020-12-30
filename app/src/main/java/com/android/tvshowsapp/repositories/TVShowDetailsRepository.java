package com.android.tvshowsapp.repositories;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.android.tvshowsapp.network.ApiClient;
import com.android.tvshowsapp.network.ApiService;
import com.android.tvshowsapp.reponses.TVShowDetailsReponses;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TVShowDetailsRepository {

    private ApiService apiService;
    public TVShowDetailsRepository(){
        apiService = ApiClient.getRetrofit().create(ApiService.class);
    }

    public LiveData<TVShowDetailsReponses> getTvShowDetails(String tvShowId){
        MutableLiveData<TVShowDetailsReponses> data = new MutableLiveData<>();
        apiService.getTvShowDetails(tvShowId).enqueue(new Callback<TVShowDetailsReponses>() {
            @Override
            public void onResponse(Call<TVShowDetailsReponses> call, @NonNull Response<TVShowDetailsReponses> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<TVShowDetailsReponses> call,@NonNull Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }
}
