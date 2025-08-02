package com.ruoyi.framework.manager;

import java.util.TimerTask;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import com.ruoyi.common.utils.Threads;
import com.ruoyi.common.utils.spring.SpringUtils;

/**
 * 异步任务管理器
 * 
 * @author ruoyi
 */
public class AsyncManager
{
    /**
     * 操作延迟10毫秒
     */
    private final int OPERATE_DELAY_TIME = 10;

    /**
     * 异步操作任务调度线程池
     */
    private ScheduledExecutorService executor;

    /**
     * 单例模式
     */
    private AsyncManager(){}

    private static AsyncManager me = new AsyncManager();

    public static AsyncManager me()
    {
        return me;
    }

    /**
     * 执行任务
     * 
     * @param task 任务
     */
    public void execute(TimerTask task)
    {
        try {
            if (executor == null) {
                synchronized (this) {
                    if (executor == null) {
                        // 使用懒加载，确保Spring容器已初始化
                        if (SpringUtils.containsBean("scheduledExecutorService")) {
                            executor = SpringUtils.getBean("scheduledExecutorService");
                        } else {
                            // 如果Spring容器未准备好，使用默认线程池
                            executor = java.util.concurrent.Executors.newScheduledThreadPool(10);
                        }
                    }
                }
            }
            if (executor != null && !executor.isShutdown()) {
                executor.schedule(task, OPERATE_DELAY_TIME, TimeUnit.MILLISECONDS);
            }
        } catch (Exception e) {
            // 如果出现异常，记录日志但不影响主流程
            System.err.println("AsyncManager execute error: " + e.getMessage());
        }
    }

    /**
     * 停止任务线程池
     */
    public void shutdown()
    {
        if (executor != null && !executor.isShutdown()) {
            Threads.shutdownAndAwaitTermination(executor);
        }
    }
}
