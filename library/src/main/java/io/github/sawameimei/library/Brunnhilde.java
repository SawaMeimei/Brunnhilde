package io.github.sawameimei.library;

/**
 * Created by huangmeng on 2017/8/5.
 */

public class Brunnhilde {

    private static Brunnhilde brunnhilde;
    ThreadPool requestExecutor = RequestExecutor.get();

    private Brunnhilde() {
    }

    public static Brunnhilde get() {
        if (brunnhilde == null) {
            synchronized (Brunnhilde.class) {
                if (brunnhilde == null) {
                    brunnhilde = new Brunnhilde();
                }
            }
        }
        return brunnhilde;
    }

    public LoadRequestCreator load(String url) {
        return new LoadRequestCreator(this, url);
    }
}
