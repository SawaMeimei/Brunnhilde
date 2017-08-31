package io.github.sawameimei.imageloder;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import io.github.sawameimei.library.Brunnhilde;
import io.github.sawameimei.sample.ImageLoader;

public class MainActivity extends AppCompatActivity {

    public static final String url = "http://img1.gamersky.com/image2017/08/20170821_zy_164_2/052_S.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ImageView ivContent = (ImageView) findViewById(R.id.iv_content);
        Brunnhilde.get(this).load(url).into(ivContent);
        /*new Thread(){
            @Override
            public void run() {
                final Bitmap imageBitmap = getImageBitmap(url);
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        ivContent.setImageBitmap(imageBitmap);
                    }
                });
            }
        }.start();*/
    }

    public Bitmap getImageBitmap(String url) {
        URL imgUrl = null;
        Bitmap bitmap = null;
        try {
            imgUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) imgUrl
                    .openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }
}
