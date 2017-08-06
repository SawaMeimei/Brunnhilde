package io.github.sawameimei.sample;

import android.graphics.Bitmap;
import android.widget.ImageView;

import java.lang.ref.WeakReference;

/**
 * Created by huangmeng on 2017/8/4.
 */
class ReadBitmapTask implements Runnable {

    private final WeakReference<ImageView> imageView;
    private final String resource;
    private final int error;
    private final Cache cache;

    public ReadBitmapTask(String resource, int error, Cache cache, ImageView imageView) {
        this.resource = resource;
        this.error = error;
        this.imageView = new WeakReference<>(imageView);
        this.cache = cache;
    }

    @Override
    public void run() {
        if (imageView.get() != null) {
            final Bitmap bitmap = cache.get(new ImageAwareImpl(imageView.get(), resource));
            if (imageView.get() != null) {
                if (bitmap != null) {
                    imageView.get().post(new Runnable() {
                        @Override
                        public void run() {
                            imageView.get().setImageBitmap(bitmap);
                        }
                    });
                } else {
                    if (error != -1) {
                        imageView.get().post(new Runnable() {
                            @Override
                            public void run() {
                                imageView.get().setImageResource(error);
                            }
                        });
                    }
                }
            }
        }
    }
}
