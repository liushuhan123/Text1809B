package com.example.mylibrary;

import android.widget.ImageView;

public interface JBannerAdapter<M> {
    void fillBannerItemData(JBanner banner, ImageView imageView, M mode, int position);
}
