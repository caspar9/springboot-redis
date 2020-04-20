package com.dyc.distributedLock;

/**
 * 分布式锁接口
 */
public interface DistributedLock {

    boolean lock(String key, String requestId);

    boolean lock(String key, String requestId, int retryTimes);

    boolean lock(String key, String requestId, int retryTimes, long sleepMillis);

    boolean lock(String key, String requestId, long expire);

    boolean lock(String key, String requestId, long expire, int retryTimes);

    boolean lock(String key, String requestId, long expire, int retryTimes, long sleepMillis);

    boolean releaseLock(String key, String requestId);
}
