package io.github.sawameimei.library;

import android.graphics.Bitmap;
import android.widget.ImageView;

import java.io.InputStream;
import java.lang.ref.WeakReference;

/**
 * Created by huangmeng on 2017/8/5.
 */

public class LoadRequest implements Runnable {

    private ThreadPool threadPool;
    private String resource;
    private MemoryCache memoryCache;
    private DiskCache diskCache;
    private Downloader downloader;
    private Decoder decoder;
    private Displayer displayer;
    private WeakReference<ImageView> target;

    private LoadRequest() {
    }

    private void setThreadPool(ThreadPool threadPool) {
        this.threadPool = threadPool;
    }

    private void setMemoryCache(MemoryCache memoryCache) {
        this.memoryCache = memoryCache;
    }

    public void setDiskCache(DiskCache diskCache) {
        this.diskCache = diskCache;
    }

    public void setDownloader(Downloader downloader) {
        this.downloader = downloader;
    }

    public void setDecoder(Decoder decoder) {
        this.decoder = decoder;
    }

    public void setDisplayer(Displayer displayer) {
        this.displayer = displayer;
    }

    public void setTarget(ImageView iv) {
        this.target = new WeakReference<>(iv);
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    @Override
    public void run() {
        if (checkTargetInvalide()) {
            return;
        }
        Bitmap bitmap = memoryCache.get(Utils.md5(resource));
        if (bitmap != null) {
            if (checkTargetInvalide()) {
                return;
            }
            displayer.display(target.get(), bitmap);
        } else {
            InputStream fileInputStream = diskCache.get(Utils.md5(resource));
            if (fileInputStream != null) {
                bitmap = this.decoder.decoder(fileInputStream, target.get());
                if (bitmap != null) {
                    memoryCache.put(Utils.md5(resource), bitmap);
                    if (checkTargetInvalide()) {
                        return;
                    }
                    displayer.display(target.get(), bitmap);
                }
            } else {
                fileInputStream = downloader.download(resource);
                if (fileInputStream != null) {
                    //diskCache.put(Utils.md5(resource), fileInputStream);
                    bitmap = this.decoder.decoder(fileInputStream, target.get());
                    if (checkTargetInvalide()) {
                        return;
                    }
                    if (bitmap != null) {
                        memoryCache.put(Utils.md5(resource), bitmap);
                        if (checkTargetInvalide()) {
                            return;
                        }
                        displayer.display(target.get(), bitmap);
                    }
                } else {
                    displayer.display(target.get(), null);
                }
            }
        }
    }

    public boolean checkTargetInvalide() {
        return target.get() == null;
    }

    public void submit() {
        this.threadPool.execute(this);
    }

    public static class LoadRequestBuilder {

        private LoadRequest loadRequest;

        public LoadRequestBuilder() {
            this.loadRequest = new LoadRequest();
        }

        public LoadRequestBuilder threadPool(ThreadPool threadPool) {
            this.loadRequest.setThreadPool(threadPool);
            return this;
        }

        public LoadRequestBuilder memoryCache(MemoryCache memoryCache) {
            this.loadRequest.setMemoryCache(memoryCache);
            return this;
        }

        public LoadRequestBuilder diskCache(DiskCache diskCache) {
            this.loadRequest.setDiskCache(diskCache);
            return this;
        }

        public LoadRequestBuilder downloader(Downloader downloader) {
            this.loadRequest.setDownloader(downloader);
            return this;
        }

        public LoadRequestBuilder decoder(Decoder decoder) {
            this.loadRequest.setDecoder(decoder);
            return this;
        }

        public LoadRequestBuilder displayer(Displayer displayer) {
            this.loadRequest.setDisplayer(displayer);
            return this;
        }

        public LoadRequestBuilder target(ImageView imageView) {
            this.loadRequest.setTarget(imageView);
            return this;
        }

        public LoadRequestBuilder resource(String resource) {
            this.loadRequest.setResource(resource);
            return this;
        }

        public LoadRequest build() {
            return loadRequest;
        }
    }
}
