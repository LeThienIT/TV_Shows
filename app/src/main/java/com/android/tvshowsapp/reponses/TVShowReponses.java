package com.android.tvshowsapp.reponses;

import com.android.tvshowsapp.models.TVShow;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TVShowReponses {
    @SerializedName("page")
    @Expose
    private int page;
    @SerializedName("pages")
    @Expose
    private int totalPages;
    @SerializedName("tv_shows")
    @Expose
    private List<TVShow> tvShows;

    public TVShowReponses(int page, int totalPages, List<TVShow> tvShows) {
        this.page = page;
        this.totalPages = totalPages;
        this.tvShows = tvShows;
    }

    public TVShowReponses() {
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List<TVShow> getTvShows() {
        return tvShows;
    }

    public void setTvShows(List<TVShow> tvShows) {
        this.tvShows = tvShows;
    }
}
