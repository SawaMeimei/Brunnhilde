package io.github.sawameimei.library;

import java.io.File;

/**
 * Created by huangmeng on 2017/8/5.
 */

public interface DiskCache {

    File get(String key);

    void put(String key, File value);

    long size();

    long maxSize();

    void clearAll();

    void clear(String keyPrefix);
}
