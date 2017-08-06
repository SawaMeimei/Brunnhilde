package io.github.sawameimei.library;

import java.io.InputStream;

/**
 * Created by huangmeng on 2017/8/5.
 */

public interface Downloader {
    InputStream download(String str);
}
