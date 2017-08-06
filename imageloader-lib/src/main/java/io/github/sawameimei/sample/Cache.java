package io.github.sawameimei.sample;

import android.graphics.Bitmap;

/**
 * Created by huangmeng on 2017/8/4.
 */
interface Cache {

    Cache getParent();

    Bitmap get(ImageAware imageAware);

    void put(Bitmap bitmap, ImageAware imageAware);

    long size();

    long maxSize();

    void clear();

    void clearKeyUri(String keyPrefix);

}
