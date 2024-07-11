package com.example.weather;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CacheEvictor {

    @Autowired
    private CacheManager cacheManager;

    @Scheduled(fixedRate = 60000) // 1 минута
    public void evictAllCachesAtIntervals() {
        cacheManager.getCache("weather").clear();
    }
}
