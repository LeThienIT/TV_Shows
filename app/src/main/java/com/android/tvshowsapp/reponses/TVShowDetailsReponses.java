package com.android.tvshowsapp.reponses;

import com.android.tvshowsapp.models.TVShowDetail;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TVShowDetailsReponses {
    @SerializedName("tvShow")
    @Expose
    private TVShowDetail tvShowDetail;

    public TVShowDetailsReponses(TVShowDetail tvShowDetail) {
        this.tvShowDetail = tvShowDetail;
    }

    public TVShowDetailsReponses() {
    }

    public TVShowDetail getTvShowDetail() {
        return tvShowDetail;
    }

    public void setTvShowDetail(TVShowDetail tvShowDetail) {
        this.tvShowDetail = tvShowDetail;
    }
}
