package io.github.sawameimei.imageloder;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import io.github.sawameimei.sample.ImageLoader;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView ivContent = (ImageView) findViewById(R.id.iv_content);
        ImageLoader.get()
                .load("this is a url")
                .placeHolder(R.mipmap.ic_launcher)
                .into(ivContent);
    }
}
