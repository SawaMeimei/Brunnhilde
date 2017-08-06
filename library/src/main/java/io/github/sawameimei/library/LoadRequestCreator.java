package io.github.sawameimei.library;

import android.widget.ImageView;

/**
 * Created by huangmeng on 2017/8/5.
 */

public class LoadRequestCreator {

    private Brunnhilde brunnhilde;
    private String resource;
    private final LoadRequest.LoadRequestBuilder requestBuilder;

    public LoadRequestCreator(Brunnhilde brunnhilde, String resource) {
        this.brunnhilde = brunnhilde;
        this.resource = resource;
        requestBuilder = new LoadRequest.LoadRequestBuilder();
    }

    public void cacheOnlyMem() {
        requestBuilder
                .memoryCache(MemoryCacheImpl.LRU)
                .diskCache(DiskCacheImpl.NO);
    }

    public void diskCacheOnly(){
        requestBuilder
                .memoryCache(MemoryCacheImpl.NO)
                .diskCache(DiskCacheImpl.LRU);
    }

    public void centerCrop(){

    }

    public void into(ImageView view) {
        requestBuilder.threadPool(brunnhilde.requestExecutor);
        requestBuilder.build().submit();
    }
}
