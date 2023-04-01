package com.yunqi.starter.web.pool;

import com.yunqi.starter.common.utils.TraceIdUtil;

import java.util.Map;
import java.util.concurrent.*;

/**
 *  线程池包装类
 * Created by @author CHQ on 2023/4/2
 */
public class ThreadPoolExecutorMdcWrapper extends ThreadPoolExecutor {


    public ThreadPoolExecutorMdcWrapper(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
                                        BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    public ThreadPoolExecutorMdcWrapper(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
                                        BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
    }

    public ThreadPoolExecutorMdcWrapper(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
                                        BlockingQueue<Runnable> workQueue, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, handler);
    }

    public ThreadPoolExecutorMdcWrapper(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
                                        BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory,
                                        RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
    }

    @Override
    public void execute(Runnable task) {
        super.execute(wrap(task));
    }

    @Override
    public <T> Future<T> submit(Runnable task, T result) {
        return super.submit(wrap(task), result);
    }

    @Override
    public <T> Future<T> submit(Callable<T> task) {
        return super.submit(wrap(task));
    }

    @Override
    public Future<?> submit(Runnable task) {
        return super.submit(wrap(task));
    }

    private <T> Callable<T> wrap(final Callable<T> callable) {
        Map<String, String> context = TraceIdUtil.getCopyOfContextMap();

        return () -> {
            if (context != null) {
                TraceIdUtil.setContextMap(context);
            }

            try {
                return callable.call();
            } finally {
                TraceIdUtil.clear();
            }
        };
    }

    private Runnable wrap(final Runnable runnable) {
        Map<String, String> context = TraceIdUtil.getCopyOfContextMap();

        return () -> {
            if (context != null) {
                TraceIdUtil.setContextMap(context);
            }

            try {
                runnable.run();
            } finally {
                TraceIdUtil.clear();
            }
        };
    }

}
