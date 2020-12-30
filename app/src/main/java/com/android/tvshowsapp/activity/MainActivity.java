package com.android.tvshowsapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.android.tvshowsapp.R;
import com.android.tvshowsapp.adapters.TVShowAdapter;
import com.android.tvshowsapp.databinding.ActivityMainBinding;
import com.android.tvshowsapp.listeners.TVShowListeners;
import com.android.tvshowsapp.models.TVShow;
import com.android.tvshowsapp.viewmodel.MostPopularTVShowsViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements TVShowListeners {

    private MostPopularTVShowsViewModel viewModel;
    private ActivityMainBinding activityMainBinding;
    private List<TVShow> tvShows = new ArrayList<>();
    private TVShowAdapter tvShowAdapter;
    private int currentPage = 1;
    private int totalAvailablePages = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(MainActivity.this, R.layout.activity_main);
//        setContentView(R.layout.activity_main);
        doInitialization();
    }

    private void doInitialization(){
        activityMainBinding.tvShowsRecyclerView.setHasFixedSize(true);
        viewModel = new ViewModelProvider(this).get(MostPopularTVShowsViewModel.class);
        tvShowAdapter = new TVShowAdapter(tvShows, this);
        activityMainBinding.tvShowsRecyclerView.setAdapter(tvShowAdapter);
        activityMainBinding.tvShowsRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(!activityMainBinding.tvShowsRecyclerView.canScrollVertically(1)){
                    if(currentPage <= totalAvailablePages){
                        currentPage += 1;
                        getMostPopularTVShows();
                    }
                }
            }
        });
        activityMainBinding.imageWatchList.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), WatchListActivity.class)));
        getMostPopularTVShows();
    }

    private void getMostPopularTVShows(){
//        activityMainBinding.setIsLoading(true);
        toggleLoading();
        viewModel.getMostPopularTVShows(currentPage).observe(this, mostPopularTVShowsResponse ->{
//                    activityMainBinding.setIsLoading(false);
                    toggleLoading();
                    if(mostPopularTVShowsResponse != null){
                        totalAvailablePages = mostPopularTVShowsResponse.getTotalPages();
                        if(mostPopularTVShowsResponse.getTvShows() != null){
                            int oldCount = tvShows.size();
                            tvShows.addAll(mostPopularTVShowsResponse.getTvShows());
                            tvShowAdapter.notifyItemRangeInserted(oldCount, tvShows.size());
                        }
                    }
                }
//                Toast.makeText(getApplicationContext(), "Total pages: "
//                        + mostPopularTVShowsResponse.getTotalPages(),
//                        Toast.LENGTH_SHORT).show()
        );
    }

    private void toggleLoading(){
        if(currentPage == 1){
            if(activityMainBinding.getIsLoading() != null && activityMainBinding.getIsLoading()){
                activityMainBinding.setIsLoading(false);
            }else{
                activityMainBinding.setIsLoading(true);
            }
        }else{
            if(activityMainBinding.getIsLoadingMore() != null && activityMainBinding.getIsLoadingMore()){
                activityMainBinding.setIsLoadingMore(false);
            }else{
                activityMainBinding.setIsLoadingMore(true);
            }
        }
    }

    @Override
    public void onTVShowClicked(TVShow tvShow) {
        Intent intent = new Intent(getApplicationContext(), TVShowDetailsActivity.class);
        intent.putExtra("tvShow", tvShow);
        startActivity(intent);
    }
}