package com.github.corneil.cloud_foundry.loader;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class LoaderScheduler {
    private LoaderService loaderService;

    public LoaderScheduler(LoaderService loaderService) {
        this.loaderService = loaderService;
    }

    @Scheduled(initialDelay = 30000, fixedDelay = 1000)
    public void invokeLoader() {
        log.info("schedule");
        loaderService.loader("schedule");
    }

    @Scheduled(initialDelay = 10000, fixedDelay = 100)
    public void invokeLoader2() {
        log.info("schedule2");
        loaderService.loader("schedule2");
    }
}
