package org.metahut.starfish.ingestion.common;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadUtils {

    private static final ExecutorService threadPoolExecutor;

    static {
        threadPoolExecutor = new ThreadPoolExecutor(8, 10, 1, TimeUnit.MINUTES, new LinkedBlockingQueue<>(5000));
    }

    public static ExecutorService getThreadPoolExecutor() {
        return threadPoolExecutor;
    }
}
