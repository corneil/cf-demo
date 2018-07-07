package com.github.corneil.cloud_foundry.loader;

import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.web.client.RestClientException;

public interface LoaderService {
    String loader(String server, String eventSource);
}
