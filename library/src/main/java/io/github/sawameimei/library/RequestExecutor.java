package io.github.sawameimei.library;

import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by huangmeng on 2017/8/5.
 */

public class RequestExecutor implements ThreadPool {

    private static ThreadPool requestExecutor;
    private ThreadPoolExecutor executor = new ThreadPoolExecutor(3, 3, 0, TimeUnit.SECONDS, new PriorityBlockingQueue<Runnable>());

    private RequestExecutor() {
    }

    public static ThreadPool get() {
        if (requestExecutor == null) {
            synchronized (RequestExecutor.class) {
                if (requestExecutor == null) {
                    requestExecutor = new RequestExecutor();
                }
            }
        }
        return requestExecutor;
    }

    @Override
    public void execute(Runnable runnable) {
        executor.execute(runnable);
    }
}
