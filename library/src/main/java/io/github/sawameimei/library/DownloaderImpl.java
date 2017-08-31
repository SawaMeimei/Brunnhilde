package io.github.sawameimei.library;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by huangmeng on 2017/8/8.
 */

class DownloaderImpl implements Downloader {

    @Override
    public InputStream download(String imageUri) {
        try {
            InputStream inputStream = createConnection(imageUri).getInputStream();
            //Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            //Log.e("bitmap", "bitmap!=null ? " + (bitmap != null));
            return inputStream;
        } catch (Exception e) {
            Log.e("error", e.getLocalizedMessage());
        }
        return null;
    }

    protected static final String ALLOWED_URI_CHARS = "@#&=*+-_.,:!?()/~'%";

    protected HttpURLConnection createConnection(String url) throws IOException {
        //String encodedUrl = Uri.encode(url, ALLOWED_URI_CHARS);
        HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
        conn.setDoInput(true);
        //conn.setRequestMethod("GET");
        //conn.setConnectTimeout(5 * 1000);
        //conn.setReadTimeout(5 * 1000);
        conn.connect();
        return conn;
    }
}
