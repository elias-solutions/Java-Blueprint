package net.springboot_java_blueprint.ratelimiting;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import static org.assertj.core.api.Assertions.assertThat;

class FixedWindowRateLimiterTest {
    private static final Duration WINDOW = Duration.ofSeconds(5);

    private final AtomicLong clock = new AtomicLong();

    @Test
    void acquire_withinPermitLimit_isGranted() throws InterruptedException {
        var limiter = new FixedWindowRateLimiter(2, WINDOW, 0, clock::get);

        assertThat(limiter.acquire()).isTrue();
        assertThat(limiter.acquire()).isTrue();
    }

    @Test
    void acquire_beyondPermitLimitWithoutQueue_isRejected() throws InterruptedException {
        var limiter = new FixedWindowRateLimiter(1, WINDOW, 0, clock::get);
        limiter.acquire();

        assertThat(limiter.acquire()).isFalse();
    }

    @Test
    void acquire_afterWindowElapsed_isGrantedAgain() throws InterruptedException {
        var limiter = new FixedWindowRateLimiter(1, WINDOW, 0, clock::get);
        limiter.acquire();

        clock.addAndGet(WINDOW.toNanos());

        assertThat(limiter.acquire()).isTrue();
    }

    @Test
    void acquire_beyondPermitLimitWithQueue_waitsForNextWindow() throws Exception {
        var window = Duration.ofMillis(200);
        var limiter = new FixedWindowRateLimiter(1, window, 1, System::nanoTime);
        limiter.acquire();

        var start = System.nanoTime();
        var queued = CompletableFuture.supplyAsync(() -> acquireUnchecked(limiter));

        assertThat(queued.get(5, TimeUnit.SECONDS)).isTrue();
        assertThat(Duration.ofNanos(System.nanoTime() - start)).isGreaterThanOrEqualTo(window.dividedBy(2));
    }

    @Test
    void acquire_whenQueueIsFull_isRejected() throws Exception {
        var limiter = new FixedWindowRateLimiter(1, Duration.ofMillis(500), 1, System::nanoTime);
        limiter.acquire();
        var queued = CompletableFuture.supplyAsync(() -> acquireUnchecked(limiter));
        TimeUnit.MILLISECONDS.sleep(100);

        assertThat(limiter.acquire()).isFalse();
        assertThat(queued.get(5, TimeUnit.SECONDS)).isTrue();
    }

    private static boolean acquireUnchecked(FixedWindowRateLimiter limiter) {
        try {
            return limiter.acquire();
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException(exception);
        }
    }
}
