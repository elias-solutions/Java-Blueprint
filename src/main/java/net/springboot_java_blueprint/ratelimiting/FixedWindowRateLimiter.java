package net.springboot_java_blueprint.ratelimiting;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
import java.util.function.LongSupplier;

/**
 * Global fixed-window limiter: grants {@code permitLimit} requests per window and lets up to
 * {@code queueLimit} further requests wait for the next window; anything beyond is rejected.
 */
public class FixedWindowRateLimiter {
    private final int permitLimit;
    private final long windowNanos;
    private final int queueLimit;
    private final LongSupplier nanoClock;

    private long windowStart;
    private int permitsUsed;
    private int queuedRequests;

    public FixedWindowRateLimiter(int permitLimit, Duration window, int queueLimit, LongSupplier nanoClock) {
        this.permitLimit = permitLimit;
        this.windowNanos = window.toNanos();
        this.queueLimit = queueLimit;
        this.nanoClock = nanoClock;
        this.windowStart = nanoClock.getAsLong();
    }

    public synchronized boolean acquire() throws InterruptedException {
        if (tryTakePermit()) {
            return true;
        }
        if (queuedRequests >= queueLimit) {
            return false;
        }

        queuedRequests++;
        try {
            while (!tryTakePermit()) {
                waitForNextWindow();
            }
            return true;
        } finally {
            queuedRequests--;
        }
    }

    private boolean tryTakePermit() {
        startNewWindowIfElapsed();
        if (permitsUsed < permitLimit) {
            permitsUsed++;
            return true;
        }
        return false;
    }

    private void startNewWindowIfElapsed() {
        var now = nanoClock.getAsLong();
        if (now - windowStart >= windowNanos) {
            windowStart = now;
            permitsUsed = 0;
        }
    }

    private void waitForNextWindow() throws InterruptedException {
        var remainingNanos = windowStart + windowNanos - nanoClock.getAsLong();
        if (remainingNanos > 0) {
            TimeUnit.NANOSECONDS.timedWait(this, remainingNanos);
        }
    }
}
