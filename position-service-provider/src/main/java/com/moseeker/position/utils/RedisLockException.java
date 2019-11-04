package com.moseeker.position.utils;

public class RedisLockException extends RuntimeException {

    public RedisLockException() {
        super();
    }

    public RedisLockException(String message) {
        super(message);
    }

    public RedisLockException(String message, Throwable cause) {
        super(message, cause);
    }

    public RedisLockException(Throwable cause) {
        super(cause);
    }
}
