package io.github.sawameimei.library;

import android.graphics.Bitmap;

import java.io.File;

/**
 * Created by huangmeng on 2017/8/5.
 */

public interface Decoder {
    Bitmap decoder(File file);
}