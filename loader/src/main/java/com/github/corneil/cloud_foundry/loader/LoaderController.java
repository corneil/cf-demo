package com.github.corneil.cloud_foundry.loader;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class LoaderController {
    private LoaderServiceImpl loaderService;

    public LoaderController(LoaderServiceImpl loaderService) {
        this.loaderService = loaderService;
    }

    @RequestMapping(path = "/load/{eventSource}", method = RequestMethod.POST)
    public ResponseEntity<String> load(@PathVariable("eventSource") String eventSource, @RequestParam("server") String server) {
        log.info("load:{},{}", eventSource, server);
        return ResponseEntity.ok(loaderService.loader(server, eventSource));
    }
}
