package com.android.tvshowsapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.android.tvshowsapp.R;
import com.android.tvshowsapp.databinding.ItemContainerEpisodesBinding;
import com.android.tvshowsapp.models.Episode;

import java.util.List;

public class EpisodesAdapter extends RecyclerView.Adapter<EpisodesAdapter.EpisodeViewHolder> {

    private List<Episode> episodes;
    private LayoutInflater layoutInflater;

    public EpisodesAdapter(List<Episode> episodes) {
        this.episodes = episodes;
    }

    @NonNull
    @Override
    public EpisodeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(layoutInflater == null){
            layoutInflater = layoutInflater.from(parent.getContext());
        }
        ItemContainerEpisodesBinding itemContainerEpisodesBinding = DataBindingUtil.inflate(layoutInflater, R.layout.item_container_episodes, parent, false);
        return new EpisodeViewHolder(itemContainerEpisodesBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull EpisodeViewHolder holder, int position) {
        holder.bindEpisode(episodes.get(position));
    }

    @Override
    public int getItemCount() {
        return episodes.size();
    }

    static class EpisodeViewHolder extends RecyclerView.ViewHolder {

        private ItemContainerEpisodesBinding itemContainerEpisodesBinding;

        public EpisodeViewHolder(@NonNull ItemContainerEpisodesBinding itemContainerEpisodesBinding) {
            super(itemContainerEpisodesBinding.getRoot());
            this.itemContainerEpisodesBinding = itemContainerEpisodesBinding;
        }

        public void bindEpisode(Episode episode) {
            String title = "S";
            String season = episode.getSeason();
            if (season.length() == 1) {
                season = "0".concat(season);
            }
            String episodesNumbers = episode.getEpisode();
            if (episodesNumbers.length() == 1) {
                episodesNumbers = "0".concat(episodesNumbers);
            }
            episodesNumbers = "E".concat(episodesNumbers);
            title = title.concat(season).concat(episodesNumbers);
            itemContainerEpisodesBinding.setTitle(title);
            itemContainerEpisodesBinding.setName(episode.getName());
            itemContainerEpisodesBinding.setAirDate(episode.getAir_date());

        }
    }
}
