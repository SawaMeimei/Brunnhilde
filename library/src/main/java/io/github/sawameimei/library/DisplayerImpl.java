package io.github.sawameimei.library;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;

/**
 * Created by huangmeng on 2017/8/8.
 */

class DisplayerImpl implements Displayer {

    @Override
    public void display(final ImageView imageView, final Bitmap bitmap) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                imageView.setImageBitmap(bitmap);
            }
        });
    }
}
