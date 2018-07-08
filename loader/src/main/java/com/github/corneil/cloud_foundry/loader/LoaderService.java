package com.github.corneil.cloud_foundry.loader;


public interface LoaderService {
    Event loader(String eventSource);
}
