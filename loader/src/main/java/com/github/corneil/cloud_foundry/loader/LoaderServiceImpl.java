package com.github.corneil.cloud_foundry.loader;

import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class LoaderServiceImpl implements LoaderService {
    private DemoClient demoClient;

    public LoaderServiceImpl(DemoClient demoClient) {
        this.demoClient = demoClient;
    }

    @Override
    @Retryable(backoff = @Backoff(delay = 500L, random = true, multiplier = 1.5), maxAttempts = 100)
    public Event loader(final String eventSource) {
        log.info("loader:{}", eventSource);
        Event event = demoClient.createEvent(eventSource);
        log.info("response:{}", event);
        return event;
    }
}
