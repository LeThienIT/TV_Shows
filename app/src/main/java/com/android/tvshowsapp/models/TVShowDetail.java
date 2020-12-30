package com.android.tvshowsapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TVShowDetail {
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("runtime")
    @Expose
    private String runtime;
    @SerializedName("image_path")
    @Expose
    private String image_path;
    @SerializedName("rating")
    @Expose
    private String rating;
    @SerializedName("genres")
    @Expose
    private String[] genres;
    @SerializedName("pictures")
    @Expose
    private String[] pictures;
    @SerializedName("episodes")
    @Expose
    private List<Episode> episodes;

    public TVShowDetail(String url, String description, String runtime, String image_path, String rating, String[] genres, String[] pictures, List<Episode> episodes) {
        this.url = url;
        this.description = description;
        this.runtime = runtime;
        this.image_path = image_path;
        this.rating = rating;
        this.genres = genres;
        this.pictures = pictures;
        this.episodes = episodes;
    }

    public TVShowDetail() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRuntime() {
        return runtime;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    public String getImage_path() {
        return image_path;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String[] getGenres() {
        return genres;
    }

    public void setGenres(String[] genres) {
        this.genres = genres;
    }

    public String[] getPictures() {
        return pictures;
    }

    public void setPictures(String[] pictures) {
        this.pictures = pictures;
    }

    public List<Episode> getEpisodes() {
        return episodes;
    }

    public void setEpisodes(List<Episode> episodes) {
        this.episodes = episodes;
    }
}
