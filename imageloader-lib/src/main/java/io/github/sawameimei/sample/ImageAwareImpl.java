package io.github.sawameimei.sample;

import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by huangmeng on 2017/8/4.
 */

public class ImageAwareImpl implements ImageAware {

    private int width;
    private int height;
    private ImageView.ScaleType scaleType;
    private String resource;

    public ImageAwareImpl(ImageView imageView,String resource) {
        final ViewGroup.LayoutParams params = imageView.getLayoutParams();
        if (params != null && params.width != ViewGroup.LayoutParams.WRAP_CONTENT) {
            width = imageView.getWidth();
        }
        if (width <= 0 && params != null) {
            width = params.width;
        }

        if (params != null && params.height != ViewGroup.LayoutParams.WRAP_CONTENT) {
            height = imageView.getHeight();
        }
        if (height <= 0 && params != null) {
            height = params.height;
        }

        this.scaleType = imageView.getScaleType();
        this.resource = resource;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public ImageView.ScaleType getScaleType() {
        return scaleType;
    }

    @Override
    public String getResource() {
        return resource;
    }
}
