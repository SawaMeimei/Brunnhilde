package io.github.sawameimei.sample;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by huangmeng on 2017/8/3.
 */
public class ImageLoader {

    private static ImageLoader imageLoader;
    private static ExecutorService requestExecutor = Executors.newFixedThreadPool(3);

    private ImageLoader() {
    }

    public static ImageLoader get() {
        if (imageLoader == null) {
            synchronized (ImageLoader.class) {
                if (imageLoader == null) {
                    imageLoader = new ImageLoader();
                }
            }
        }
        return imageLoader;
    }

    public ImageLoadRequest load(String url){
        return new ImageLoadRequest(url);
    }

    void submitReadBitmapTask(Runnable runnable){
        requestExecutor.execute(runnable);
    }
}
