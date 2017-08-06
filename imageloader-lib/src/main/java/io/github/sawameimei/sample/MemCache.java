package io.github.sawameimei.sample;

import android.graphics.Bitmap;

/**
 * Created by huangmeng on 2017/8/4.
 */

class MemCache implements Cache {

    private static LruCache lruCache = new LruCache(Utils.calculateMemoryCacheSize(ImageLoaderProvider.context));
    private static Cache memCache;

    private MemCache() {
    }

    public static Cache instance() {
        if (memCache == null) {
            synchronized (MemCache.class) {
                if (memCache == null) {
                    memCache = new MemCache();
                }
            }
        }
        return memCache;
    }

    @Override
    public Cache getParent() {
        return DiskCache.instance();
    }

    @Override
    public Bitmap get(ImageAware imageAware) {
        Bitmap bitmap = lruCache.get(Utils.generateKeyFromImageAware(imageAware));
        if (bitmap != null) {
            return bitmap;
        } else {
            Cache parent = getParent();
            if (parent != null) {
                bitmap = parent.get(imageAware);
                put(bitmap, imageAware);
                return bitmap;
            }
        }
        return null;
    }

    @Override
    public void put(Bitmap bitmap, ImageAware imageAware) {
        lruCache.set(Utils.generateKeyFromImageAware(imageAware), bitmap);
    }

    @Override
    public long size() {
        return lruCache.size();
    }

    @Override
    public long maxSize() {
        return lruCache.maxSize();
    }

    @Override
    public void clear() {
        lruCache.clear();
    }

    @Override
    public void clearKeyUri(String keyPrefix) {
        lruCache.clearKeyUri(keyPrefix);
    }
}
