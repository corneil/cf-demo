package com.github.corneil.cloud_foundry.loader;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class LoaderController {
    private LoaderService loaderService;

    public LoaderController(LoaderService loaderService) {
        this.loaderService = loaderService;
    }

    @RequestMapping(path = "/load/{eventSource}", method = RequestMethod.POST)
    public ResponseEntity<Event> load(@PathVariable("eventSource") String eventSource) {
        log.info("load:{}", eventSource);
        return ResponseEntity.ok(loaderService.loader(eventSource));
    }
}
