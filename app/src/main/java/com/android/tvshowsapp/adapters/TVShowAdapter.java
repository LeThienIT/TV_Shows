package com.android.tvshowsapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.android.tvshowsapp.R;
import com.android.tvshowsapp.databinding.ItemContainerTvShowBinding;
import com.android.tvshowsapp.listeners.TVShowListeners;
import com.android.tvshowsapp.models.TVShow;

import java.util.List;

public class TVShowAdapter extends RecyclerView.Adapter<TVShowAdapter.TVShowViewHolder>{

    private List<TVShow> tvShows;
    private LayoutInflater layoutInflater;
    private TVShowListeners tvShowListeners;

    public TVShowAdapter(List<TVShow> tvShows, TVShowListeners tvShowListeners) {
        this.tvShows = tvShows;
        this.tvShowListeners = tvShowListeners;
    }

    @NonNull
    @Override
    public TVShowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(layoutInflater == null){
            layoutInflater = layoutInflater.from(parent.getContext());
        }
        ItemContainerTvShowBinding tvShowBinding = DataBindingUtil.inflate(layoutInflater, R.layout.item_container_tv_show, parent, false);
        return new TVShowViewHolder(tvShowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull TVShowViewHolder holder, int position) {
        holder.bindTVShow(tvShows.get(position));
    }

    @Override
    public int getItemCount() {
        return tvShows.size();
    }

    class TVShowViewHolder extends RecyclerView.ViewHolder{

        private ItemContainerTvShowBinding itemContainerTvShowBinding;

        public TVShowViewHolder(@NonNull ItemContainerTvShowBinding itemContainerTvShowBinding) {
            super(itemContainerTvShowBinding.getRoot());
            this.itemContainerTvShowBinding = itemContainerTvShowBinding;
        }

        public void bindTVShow(TVShow tvShow){
            itemContainerTvShowBinding.setTvShow(tvShow);
            itemContainerTvShowBinding.executePendingBindings();
            itemContainerTvShowBinding.getRoot().setOnClickListener(v -> tvShowListeners.onTVShowClicked(tvShow));
        }
    }
}
