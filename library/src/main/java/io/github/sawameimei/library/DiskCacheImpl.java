package io.github.sawameimei.library;

import android.content.Context;

import com.jakewharton.disklrucache.DiskLruCache;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by huangmeng on 2017/8/6.
 */

public enum DiskCacheImpl implements DiskCache {

    NO {
        @Override
        DiskCacheImpl init(Context context) {
            return this;
        }

        @Override
        public InputStream get(String key) {
            return null;
        }

        @Override
        public void put(String key, InputStream value) {

        }

        @Override
        public long size() {
            return 0;
        }

        @Override
        public long maxSize() {
            return 0;
        }

        @Override
        public void clearAll() {

        }

        @Override
        public void clear(String keyPrefix) {

        }
    },
    LRU {
        @Override
        DiskCacheImpl init(Context context) {
            this.maxSize = 200 * 1024 * 1024;
            try {
                lruCache = DiskLruCache.open(Utils.getDiskCacheDir(context, "image"), Utils.getAppVersion(context), 1, maxSize);
            } catch (IOException e) {
            }
            return this;
        }

        @Override
        public InputStream get(String key) {
            if (lruCache != null) {
                try {
                    DiskLruCache.Snapshot snapshot = lruCache.get(key);
                    if (snapshot != null) {
                        return snapshot.getInputStream(0);
                    }
                } catch (IOException e) {
                }
            }
            return null;
        }

        @Override
        public void put(String key, InputStream value) {
            if (lruCache != null && value != null) {
                OutputStream outputStream = null;
                InputStream inputStream = value;
                try {
                    DiskLruCache.Editor edit = lruCache.edit(key);
                    outputStream = edit.newOutputStream(0);
                    int b;
                    while ((b = inputStream.read()) != -1) {
                        outputStream.write(b);
                    }
                } catch (Exception e) {
                } finally {
                    try {
                        if (outputStream != null) {
                            outputStream.close();
                        }
                        inputStream.close();
                    } catch (Exception e) {
                    }
                }
            }
        }

        @Override
        public long size() {
            if (lruCache != null) {
                return lruCache.size();
            }
            return 0;
        }

        @Override
        public long maxSize() {
            if (lruCache != null) {
                return lruCache.getMaxSize();
            }
            return 0;
        }

        @Override
        public void clearAll() {
            if (lruCache != null) {
                try {
                    lruCache.delete();
                } catch (IOException e) {
                }
            }
        }

        @Override
        public void clear(String keyPrefix) {
            if (lruCache != null) {
                try {
                    lruCache.remove(keyPrefix);
                } catch (IOException e) {
                }
            }
        }
    };

    protected long maxSize;
    protected DiskLruCache lruCache;

    abstract DiskCacheImpl init(Context context);
}
