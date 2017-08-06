package io.github.sawameimei.library;

import android.widget.ImageView;

/**
 * Created by huangmeng on 2017/8/5.
 */

public class LoadRequestCreator {

    private Brunnhilde brunnhilde;
    private String resource;
    private final LoadRequest.LoadRequestBuilder requestBulider;

    public LoadRequestCreator(Brunnhilde brunnhilde, String resource) {
        this.brunnhilde = brunnhilde;
        this.resource = resource;
        requestBulider = new LoadRequest.LoadRequestBuilder();
    }

    public void into(ImageView view){
        requestBulider.threadPool(brunnhilde.requestExecutor);
        requestBulider.build().submit();
    }
}
