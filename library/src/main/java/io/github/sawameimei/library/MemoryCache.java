package io.github.sawameimei.library;

import android.graphics.Bitmap;

/**
 * Created by huangmeng on 2017/8/5.
 */

public interface MemoryCache {

    Bitmap get(String key);

    void put(String key, Bitmap value);

    long size();

    long maxSize();

    void clearAll();

    void clear(String keyPrefix);

}
