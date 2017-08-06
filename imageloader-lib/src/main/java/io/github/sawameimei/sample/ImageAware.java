package io.github.sawameimei.sample;

import android.widget.ImageView;

/**
 * Created by huangmeng on 2017/8/4.
 */
interface ImageAware {

    int getWidth();

    int getHeight();

    ImageView.ScaleType getScaleType();

    String getResource();

}
