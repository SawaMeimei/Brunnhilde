package io.github.sawameimei.library;

import com.jakewharton.disklrucache.DiskLruCache;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by huangmeng on 2017/8/6.
 */

public enum DiskCacheImpl implements DiskCache {

    NO(0) {
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
    LRU(1024 * 1024 * 200) {
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

    protected final long maxSize;
    protected DiskLruCache lruCache;

    DiskCacheImpl(long maxSize) {
        this.maxSize = maxSize;
        try {
            lruCache = DiskLruCache.open(Utils.getDiskCacheDir(BrunnhildeProvider.context, "image"), Utils.getAppVersion(BrunnhildeProvider.context), 1, maxSize);
        } catch (IOException e) {
        }
    }
}
