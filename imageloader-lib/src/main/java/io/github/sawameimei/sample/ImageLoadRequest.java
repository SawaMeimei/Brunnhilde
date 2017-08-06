package io.github.sawameimei.sample;

import android.widget.ImageView;

/**
 * Created by huangmeng on 2017/8/3.
 */

public class ImageLoadRequest {

    private String resource;
    private int placeHolder = -1;
    private int error = -1;
    private Cache cache = MemCache.instance();

    public ImageLoadRequest(String resource) {
        this.resource = resource;
    }

    public ImageLoadRequest placeHolder(int resId) {
        this.placeHolder = resId;
        return this;
    }

    public ImageLoadRequest error(int resId) {
        this.error = resId;
        return this;
    }

    public ImageLoadRequest onlyDiskCache() {
        this.cache = DiskCache.instance();
        return this;
    }

    public void into(ImageView imageView) {
        if (placeHolder != -1) {
            imageView.setImageResource(placeHolder);
        }
        ImageLoader.get().submitReadBitmapTask(new ReadBitmapTask(resource, error, cache, imageView));
    }
}
