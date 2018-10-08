package com.opengdansk.estimate.service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.opengdansk.configuration.ZtmApiConfiguration;
import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ExecutorServiceProvider {

    @Getter
    private final ExecutorService executorService;

    @Autowired
    public ExecutorServiceProvider(ZtmApiConfiguration configuration) {
        executorService = Executors.newFixedThreadPool(configuration.getExecutorPoolCount());
    }
}
