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
        requestBuilder = new LoadRequest.LoadRequestBuilder()
                .resource(resource)
                .downloader(brunnhilde.downloader)
                .displayer(brunnhilde.displayer)
                .threadPool(brunnhilde.requestExecutor)
                .memoryCache(brunnhilde.memoryCache)
                .decoder(brunnhilde.decoder)
                .diskCache(brunnhilde.diskCache);
    }

    public LoadRequestCreator cacheOnlyMem() {
        requestBuilder
                .memoryCache(MemoryCacheImpl.LRU)
                .diskCache(DiskCacheImpl.NO);
        return this;
    }

    public LoadRequestCreator diskCacheOnly() {
        requestBuilder
                .memoryCache(MemoryCacheImpl.NO)
                .diskCache(DiskCacheImpl.LRU);
        return this;
    }

    public void into(ImageView view) {
        requestBuilder.target(view).build().submit();
    }
}
