package io.github.sawameimei.library;

import android.graphics.Bitmap;
import android.widget.ImageView;

import java.io.InputStream;

/**
 * Created by huangmeng on 2017/8/5.
 */

public interface Decoder {
    Bitmap decoder(InputStream file, ImageView imageView);
}
