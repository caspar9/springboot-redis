package com.dyc.distributedLock;

/**
 * 抽象类，实现基本的方法，关键方法由子类去实现
 */
public abstract class AbstractDistributedLock implements DistributedLock {

    //默认超时时间
    private static final long EXPIRE_MILLIS = 10000; //10 * 60 * 1000;

    //默认重试次数
    private static final int RETRY_TIMES = 3;

    //默认重试间隔时间
    private static final long SLEEP_MILLIS = 500;

    @Override
    public boolean lock(String key, String requestId) {
        return lock(key, requestId, EXPIRE_MILLIS, RETRY_TIMES, SLEEP_MILLIS);
    }

    @Override
    public boolean lock(String key, String requestId, int retryTimes) {
        return lock(key, requestId, EXPIRE_MILLIS, retryTimes, SLEEP_MILLIS);
    }

    @Override
    public boolean lock(String key, String requestId, int retryTimes, long sleepMillis) {
        return lock(key, requestId, EXPIRE_MILLIS, retryTimes, sleepMillis);
    }

    @Override
    public boolean lock(String key, String requestId, long expire) {
        return lock(key, requestId, expire, RETRY_TIMES, SLEEP_MILLIS);
    }

    @Override
    public boolean lock(String key, String requestId, long expire, int retryTimes) {
        return lock(key, requestId, expire, retryTimes, SLEEP_MILLIS);
    }
}
