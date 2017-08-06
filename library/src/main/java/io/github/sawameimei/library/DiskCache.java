package io.github.sawameimei.library;

import java.io.File;
import java.io.InputStream;

/**
 * Created by huangmeng on 2017/8/5.
 */

public interface DiskCache {

    InputStream get(String key);

    void put(String key, InputStream value);

    long size();

    long maxSize();

    void clearAll();

    void clear(String keyPrefix);
}
