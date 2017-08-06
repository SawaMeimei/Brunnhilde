package io.github.sawameimei.sample;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.jakewharton.disklrucache.DiskLruCache;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by huangmeng on 2017/8/4.
 */

public class DiskCache implements Cache {

    private static final long cacheSize = 1024 * 1024 * 200;
    private static Cache diskCache;
    private static DiskLruCache lruCache;

    private DiskCache() {
        try {
            lruCache = DiskLruCache.open(Utils.getDiskCacheDir(ImageLoaderProvider.context, "image"), Utils.getAppVersion(ImageLoaderProvider.context), 1, cacheSize);
        } catch (IOException e) {
        }
    }

    public static Cache instance() {
        if (diskCache == null) {
            synchronized (MemCache.class) {
                if (diskCache == null) {
                    diskCache = new DiskCache();
                }
            }
        }
        return diskCache;
    }

    @Override
    public Cache getParent() {
        return null;
    }

    @Override
    public Bitmap get(ImageAware imageAware) {
        if (lruCache != null) {
            try {
                DiskLruCache.Snapshot snapshot = lruCache.get(Utils.generateKeyFromImageAware(imageAware));
                if (snapshot != null) {
                    InputStream is = snapshot.getInputStream(0);
                    return BitmapFactory.decodeStream(is);
                }
            } catch (IOException e) {
            }
        }
        return null;
    }

    @Override
    public void put(Bitmap bitmap, ImageAware imageAware) {

    }

    @Override
    public long size() {
        return lruCache!=null? lruCache.size() :0;
    }

    @Override
    public long maxSize() {
        return cacheSize;
    }

    @Override
    public void clear() {
        try {
            if (lruCache != null) {
                lruCache.delete();
            }
        } catch (IOException e) {
        }
    }

    @Override
    public void clearKeyUri(String keyPrefix) {
        try {
            if (lruCache != null) {
                lruCache.remove(keyPrefix);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
