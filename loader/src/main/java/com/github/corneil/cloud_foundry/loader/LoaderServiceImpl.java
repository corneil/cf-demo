package com.github.corneil.cloud_foundry.loader;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

@Service
@Slf4j
public class LoaderServiceImpl implements LoaderService {
    private String lastServer;

    public LoaderServiceImpl() {
    }

    @Override
    @Retryable(value = RestClientException.class, backoff = @Backoff(delay = 500L), maxAttempts = 10)
    public String loader(final String server, final String eventSource) {
        log.info("loader:{},{}", server, eventSource);
        if (server != null) {
            lastServer = server;
        }
        Assert.notNull(lastServer, "server name is required");
        URI uri;
        try {
            uri = new URI(String.format("http://%s/event/%s", lastServer, eventSource));
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException(String.format("Invalid server name:%s", server));
        }
        RequestEntity<?> request = RequestEntity.post(uri).build();
        RestTemplate restTemplate = new RestTemplate();
        log.info("invoking:{}", uri);
        ResponseEntity<String> response = restTemplate.exchange(request, String.class);
        log.info("loader:{}", response);
        return response.getBody();
    }
}
