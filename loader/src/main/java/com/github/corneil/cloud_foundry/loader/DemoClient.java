package com.github.corneil.cloud_foundry.loader;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("cf-demo")
public interface DemoClient {
    @RequestMapping(path = "/event/{eventSource}", method = RequestMethod.POST)
    Event createEvent(@PathVariable("eventSource") String eventSource);
}
