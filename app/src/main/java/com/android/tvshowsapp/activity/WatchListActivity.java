package com.android.tvshowsapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.Toast;

import com.android.tvshowsapp.R;
import com.android.tvshowsapp.databinding.ActivityWatchListBinding;
import com.android.tvshowsapp.viewmodel.WatchListViewModel;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class WatchListActivity extends AppCompatActivity {

    private ActivityWatchListBinding activityWatchListBinding;
    private WatchListViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityWatchListBinding = DataBindingUtil.setContentView(this, R.layout.activity_watch_list);
        doInitialization();
    }

    private void doInitialization() {
        viewModel = new ViewModelProvider(this).get(WatchListViewModel.class);
        activityWatchListBinding.imageBack.setOnClickListener(v -> onBackPressed());
    }

    private void loadWatchList(){
        activityWatchListBinding.setIsLoading(true);
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(viewModel.loadWatchList().subscribeOn(Schedulers.computation())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(tvShows -> {
            activityWatchListBinding.setIsLoading(false);
            Toast.makeText(getApplicationContext(), "WatchList: " + tvShows.size(), Toast.LENGTH_SHORT).show();
        }));
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadWatchList();
    }
}