package io.github.sawameimei.library;

import android.graphics.Bitmap;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by huangmeng on 2017/8/6.
 */

public enum MemoryCacheImpl implements MemoryCache {

    NO(0) {
        @Override
        public Bitmap get(String key) {
            return null;
        }

        @Override
        public void put(String key, Bitmap value) {

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
    LRU(Utils.calculateMemoryCacheSize(BrunnhildeProvider.context)) {

        private long size;

        @Override
        public Bitmap get(String key) {
            if (key == null) {
                throw new NullPointerException("key == null");
            }

            Bitmap mapValue;
            synchronized (this) {
                mapValue = map.get(key);
                if (mapValue != null) {
                    return mapValue;
                }
            }
            return null;
        }

        @Override
        public void put(String key, Bitmap value) {
            if (key == null || value == null) {
                throw new NullPointerException("key == null || bitmap == null");
            }

            int addedSize = Utils.getBitmapBytes(value);
            if (addedSize > maxSize) {
                return;
            }

            synchronized (this) {
                size += addedSize;
                Bitmap previous = map.put(key, value);
                if (previous != null) {
                    size -= Utils.getBitmapBytes(previous);
                }
            }

            trimToSize(maxSize);
        }

        private void trimToSize(long maxSize) {
            while (true) {
                String key;
                Bitmap value;
                synchronized (this) {
                    if (size < 0 || (map.isEmpty() && size != 0)) {
                        throw new IllegalStateException(
                                getClass().getName() + ".sizeOf() is reporting inconsistent results!");
                    }

                    if (size <= maxSize || map.isEmpty()) {
                        break;
                    }

                    Map.Entry<String, Bitmap> toEvict = map.entrySet().iterator().next();
                    key = toEvict.getKey();
                    value = toEvict.getValue();
                    map.remove(key);
                    size -= Utils.getBitmapBytes(value);
                }
            }
        }

        @Override
        public long size() {
            return size;
        }

        @Override
        public long maxSize() {
            return maxSize;
        }

        @Override
        public void clearAll() {
            trimToSize(-1);
        }

        @Override
        public void clear(String keyPrefix) {
            int uriLength = keyPrefix.length();
            for (Iterator<Map.Entry<String, Bitmap>> i = map.entrySet().iterator(); i.hasNext(); ) {
                Map.Entry<String, Bitmap> entry = i.next();
                String key = entry.getKey();
                Bitmap value = entry.getValue();
                int newlineIndex = key.indexOf('\n');
                if (newlineIndex == uriLength && key.substring(0, newlineIndex).equals(keyPrefix)) {
                    i.remove();
                    size -= Utils.getBitmapBytes(value);
                }
            }
        }
    };

    protected LinkedHashMap<String, Bitmap> map;
    protected long maxSize;

    MemoryCacheImpl(long maxSize) {
        this.maxSize = maxSize;
        if (maxSize != 0) {
            this.map = new LinkedHashMap<>(0, 0.75f, true);
        }
    }
}
