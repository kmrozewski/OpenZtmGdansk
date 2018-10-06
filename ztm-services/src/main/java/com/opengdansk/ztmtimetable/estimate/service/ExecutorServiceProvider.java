package com.opengdansk.ztmtimetable.estimate.service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.opengdansk.ztmtimetable.estimate.configuration.ZtmApiConfiguration;

@Component
public class ExecutorServiceProvider {

    @Getter
    private final ExecutorService executorService;

    @Autowired
    public ExecutorServiceProvider(ZtmApiConfiguration configuration) {
        executorService = Executors.newFixedThreadPool(configuration.getExecutorPoolCount());
    }
}
