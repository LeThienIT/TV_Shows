package com.android.tvshowsapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.android.tvshowsapp.R;
import com.android.tvshowsapp.databinding.ItemContainerSlideImageBinding;



public class ImageSliderAdapter extends RecyclerView.Adapter<ImageSliderAdapter.ImageSliderViewHolder>{

    private String[] sliderImages;
    private LayoutInflater layoutInflater;

    public ImageSliderAdapter(String[] sliderImages) {
        this.sliderImages = sliderImages;
    }

    @NonNull
    @Override
    public ImageSliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(layoutInflater == null){
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        ItemContainerSlideImageBinding slideImageBinding
                = DataBindingUtil.inflate(layoutInflater, R.layout.item_container_slide_image, parent, false);
        return new ImageSliderViewHolder(slideImageBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageSliderViewHolder holder, int position) {
        holder.bindSliderImage(sliderImages[position]);
    }

    @Override
    public int getItemCount() {
        return sliderImages.length;
    }


    static class ImageSliderViewHolder extends RecyclerView.ViewHolder {

        private ItemContainerSlideImageBinding itemContainerSlideImageBinding;

        public ImageSliderViewHolder(@NonNull ItemContainerSlideImageBinding itemContainerSlideImageBinding) {
            super(itemContainerSlideImageBinding.getRoot());
            this.itemContainerSlideImageBinding = itemContainerSlideImageBinding;
        }

        public void bindSliderImage(String imageURL) {
            itemContainerSlideImageBinding.setImageURL(imageURL);
        }
    }
}
