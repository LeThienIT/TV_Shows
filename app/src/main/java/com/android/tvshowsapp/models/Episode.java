package com.android.tvshowsapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Episode {
    @SerializedName("season")
    @Expose
    private String season;
    @SerializedName("episode")
    @Expose
    private String episode;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("air_date")
    @Expose
    private String air_date;

    public Episode(String season, String episode, String name, String air_date) {
        this.season = season;
        this.episode = episode;
        this.name = name;
        this.air_date = air_date;
    }

    public Episode() {
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public String getEpisode() {
        return episode;
    }

    public void setEpisode(String episode) {
        this.episode = episode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAir_date() {
        return air_date;
    }

    public void setAir_date(String air_date) {
        this.air_date = air_date;
    }
}
