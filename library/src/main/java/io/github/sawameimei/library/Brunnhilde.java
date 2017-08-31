package io.github.sawameimei.library;

import android.content.Context;

import java.lang.ref.WeakReference;

/**
 * Created by huangmeng on 2017/8/5.
 */

public class Brunnhilde {

    private static Brunnhilde brunnhilde;
    ThreadPool requestExecutor = RequestExecutor.get();
    MemoryCache memoryCache = MemoryCacheImpl.LRU;
    DiskCache diskCache;
    Decoder decoder = new DecoderImpl();
    Downloader downloader = new DownloaderImpl();
    Displayer displayer = new DisplayerImpl();
    private static WeakReference<Context> context;

    private Brunnhilde(Context context) {
        this.context = new WeakReference<>(context);
        diskCache = DiskCacheImpl.LRU.init(context);
    }

    public static Brunnhilde get(Context context) {
        if (brunnhilde == null) {
            synchronized (Brunnhilde.class) {
                if (brunnhilde == null) {
                    brunnhilde = new Brunnhilde(context);
                }
            }
        }
        return brunnhilde;
    }

    public static Context getContext() {
        return context.get();
    }


    public LoadRequestCreator load(String url) {
        return new LoadRequestCreator(this, url);
    }
}
