package com.android.tvshowsapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.text.HtmlCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ResourceCursorAdapter;
import android.widget.Toast;

import com.android.tvshowsapp.R;
import com.android.tvshowsapp.adapters.EpisodesAdapter;
import com.android.tvshowsapp.adapters.ImageSliderAdapter;
import com.android.tvshowsapp.databinding.ActivityTVShowDetailsBinding;
import com.android.tvshowsapp.databinding.LayoutEpisodesBottomSheetBinding;
import com.android.tvshowsapp.models.TVShow;
import com.android.tvshowsapp.viewmodel.TVShowDetailsViewModel;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.Locale;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;


public class TVShowDetailsActivity extends AppCompatActivity {

    private ActivityTVShowDetailsBinding activityTVShowDetailsBinding;
    private TVShowDetailsViewModel tvShowDetailsViewModel;
    private BottomSheetDialog episodesBottomSheetDialog;
    private LayoutEpisodesBottomSheetBinding layoutEpisodesBottomSheetBinding;
    private TVShow tvShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityTVShowDetailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_t_v_show_details);
        doInitialization();
    }

    private void doInitialization() {
        tvShowDetailsViewModel = new ViewModelProvider(this).get(TVShowDetailsViewModel.class);
        activityTVShowDetailsBinding.imageBack.setOnClickListener(v -> onBackPressed());
        tvShow = (TVShow) getIntent().getSerializableExtra("tvShow");
        getTVShowDetails();
    }

    private void getTVShowDetails() {
        activityTVShowDetailsBinding.setIsloading(true);
        String tvShowId = String.valueOf(tvShow.getId());
        tvShowDetailsViewModel.getTVShowDetails(tvShowId)
                .observe(this, tvShowDetailsReponses -> {
                            activityTVShowDetailsBinding.setIsloading(false);
//                            Toast.makeText(getApplicationContext()
//                                    , tvShowDetailsReponses.getTvShowDetail().getUrl(), Toast.LENGTH_SHORT).show();
                            if (tvShowDetailsReponses.getTvShowDetail() != null) {
                                if (tvShowDetailsReponses.getTvShowDetail().getPictures() != null) {
                                    loadImagesSlider(tvShowDetailsReponses.getTvShowDetail().getPictures());
                                }
                                activityTVShowDetailsBinding.setTvShowImageURL(tvShowDetailsReponses.getTvShowDetail().getImage_path());
                                activityTVShowDetailsBinding.imageTVShow.setVisibility(View.VISIBLE);
                                activityTVShowDetailsBinding.setDescription(String.valueOf(HtmlCompat.fromHtml(tvShowDetailsReponses.getTvShowDetail().getDescription(), HtmlCompat.FROM_HTML_MODE_LEGACY)));
                                activityTVShowDetailsBinding.textDescription.setVisibility(View.VISIBLE);
                                activityTVShowDetailsBinding.textReadMore.setVisibility(View.VISIBLE);
                                activityTVShowDetailsBinding.textReadMore.setOnClickListener(v -> {
                                    if (activityTVShowDetailsBinding.textReadMore.getText().toString().equals("Read More")) {
                                        activityTVShowDetailsBinding.textDescription.setMaxLines(Integer.MAX_VALUE);
                                        activityTVShowDetailsBinding.textDescription.setEllipsize(null);
                                        activityTVShowDetailsBinding.textReadMore.setText("Read Less");
                                    } else {
                                        activityTVShowDetailsBinding.textDescription.setMaxLines(4);
                                        activityTVShowDetailsBinding.textDescription.setEllipsize(TextUtils.TruncateAt.END);
                                        activityTVShowDetailsBinding.textReadMore.setText(R.string.read_more);
                                    }
                                });
                                activityTVShowDetailsBinding.setRating(String.format(Locale.getDefault(), "%.2f"
                                        , Double.parseDouble(tvShowDetailsReponses.getTvShowDetail().getRating())));
                                if (tvShowDetailsReponses.getTvShowDetail().getGenres() != null) {
                                    activityTVShowDetailsBinding.setGenre(tvShowDetailsReponses.getTvShowDetail().getGenres()[0]);
                                } else {
                                    activityTVShowDetailsBinding.setGenre("N/A");
                                }
                                activityTVShowDetailsBinding.setRuntime(tvShowDetailsReponses.getTvShowDetail().getRuntime() + "Min");
                                activityTVShowDetailsBinding.viewDivider1.setVisibility(View.VISIBLE);
                                activityTVShowDetailsBinding.layoutMisc.setVisibility(View.VISIBLE);
                                activityTVShowDetailsBinding.viewDivider2.setVisibility(View.VISIBLE);
                                activityTVShowDetailsBinding.buttonWebsite.setOnClickListener(v -> {
                                    Intent intent = new Intent(Intent.ACTION_VIEW);
                                    intent.setData(Uri.parse(tvShowDetailsReponses.getTvShowDetail().getUrl()));
                                    startActivity(intent);
                                });
                                activityTVShowDetailsBinding.buttonWebsite.setVisibility(View.VISIBLE);
                                activityTVShowDetailsBinding.buttonEpisodes.setVisibility(View.VISIBLE);
                                activityTVShowDetailsBinding.buttonEpisodes.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if (episodesBottomSheetDialog == null) {
                                            episodesBottomSheetDialog = new BottomSheetDialog(TVShowDetailsActivity.this);
                                            layoutEpisodesBottomSheetBinding = DataBindingUtil.inflate(LayoutInflater
                                                            .from(TVShowDetailsActivity.this)
                                                    , R.layout.layout_episodes_bottom_sheet
                                                    , findViewById(R.id.episodesContainer)
                                                    , false);
                                            episodesBottomSheetDialog.setContentView(layoutEpisodesBottomSheetBinding.getRoot());
                                            layoutEpisodesBottomSheetBinding.episodesRecyclerView.setAdapter(new EpisodesAdapter(tvShowDetailsReponses.getTvShowDetail().getEpisodes()));
                                            layoutEpisodesBottomSheetBinding.textTitle.setText(String.format("Episodes | %s", tvShow.getName()));
                                            layoutEpisodesBottomSheetBinding.imageClose.setOnClickListener(v1 -> episodesBottomSheetDialog.dismiss());
                                        }
                                        // -- Optional section start --
                                        FrameLayout frameLayout = episodesBottomSheetDialog.findViewById(com.google.android.material.R.id.design_bottom_sheet);
                                        if (frameLayout != null) {
                                            BottomSheetBehavior<View> bottomSheetBehavior = BottomSheetBehavior.from(frameLayout);
                                            bottomSheetBehavior.setPeekHeight(Resources.getSystem().getDisplayMetrics().heightPixels);
                                            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                                        }

                                        // -- Optional section end --
                                        episodesBottomSheetDialog.show();
                                    }
                                });
                                activityTVShowDetailsBinding.imageWatchList.setOnClickListener(v -> new CompositeDisposable().add(tvShowDetailsViewModel
                                        .addToWatchList(tvShow)
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread()).subscribe(() -> {
                                            activityTVShowDetailsBinding.imageWatchList.setImageResource(R.drawable.ic_check);
                                            Toast.makeText(getApplicationContext(), "Added to watchlist", Toast.LENGTH_SHORT).show();
                                        })
                                ));
                                activityTVShowDetailsBinding.imageWatchList.setVisibility(View.VISIBLE);
                                loadBasicTVShowDetails();
                            }
                        }
                );
    }

    private void loadImagesSlider(String[] slidersImages) {
        activityTVShowDetailsBinding.sliderViewPager.setOffscreenPageLimit(1);
        activityTVShowDetailsBinding.sliderViewPager.setAdapter(new ImageSliderAdapter(slidersImages));
        activityTVShowDetailsBinding.sliderViewPager.setVisibility(View.VISIBLE);
        activityTVShowDetailsBinding.viewFadingEdge.setVisibility(View.VISIBLE);
        setupSliderIndicator(slidersImages.length);
        activityTVShowDetailsBinding.sliderViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setCurrentSliderIndicator(position);
            }
        });
    }

    public void setupSliderIndicator(int count) {
        ImageView[] indicators = new ImageView[count];
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(8, 0, 8, 0);
        for (int i = 0; i < indicators.length; i++) {
            indicators[i] = new ImageView(getApplicationContext());
            indicators[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.background_slider_indicator_inactive));
            indicators[i].setLayoutParams(layoutParams);
            activityTVShowDetailsBinding.layoutSliderIndicators.addView(indicators[i]);
        }
        activityTVShowDetailsBinding.layoutSliderIndicators.setVisibility(View.VISIBLE);
        setCurrentSliderIndicator(0);
    }

    private void setCurrentSliderIndicator(int position) {
        int childCount = activityTVShowDetailsBinding.layoutSliderIndicators.getChildCount();
        for (int i = 0; i < childCount; i++) {
            ImageView imageView = (ImageView) activityTVShowDetailsBinding.layoutSliderIndicators.getChildAt(i);
            if (i == position) {
                imageView.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.background_slider_indicator_active));
            } else {
                imageView.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.background_slider_indicator_inactive));
            }
        }
    }

    private void loadBasicTVShowDetails() {
        activityTVShowDetailsBinding.setTvShowName(tvShow.getName());
        activityTVShowDetailsBinding.setNetworkCountry(tvShow.getNetwork() + " (" + tvShow.getCountry() + ")");
        activityTVShowDetailsBinding.setStatus(tvShow.getStatus());
        activityTVShowDetailsBinding.setStartedDate(tvShow.getStart_date());
        activityTVShowDetailsBinding.textName.setVisibility(View.VISIBLE);
        activityTVShowDetailsBinding.textNetworkCountry.setVisibility(View.VISIBLE);
        activityTVShowDetailsBinding.textStatus.setVisibility(View.VISIBLE);
        activityTVShowDetailsBinding.textStarted.setVisibility(View.VISIBLE);
    }
}