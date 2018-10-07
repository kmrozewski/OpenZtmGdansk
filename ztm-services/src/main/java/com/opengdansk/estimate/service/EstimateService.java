package com.opengdansk.estimate.service;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import com.opengdansk.estimate.model.Delay;
import com.opengdansk.estimate.model.EstimateResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.val;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.opengdansk.estimate.client.RestfulClient;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EstimateService {

    @NonNull
    private final RestfulClient client;

    @NonNull
    private final ExecutorServiceProvider executorService;

    public EstimateResponse getEstimate(String stopId) {
        return client.getEstimate(stopId);
    }

    public EstimateResponse getEstimateTable(List<String> stopIds) {
        val tasks = startEstimateTasks(stopIds);
        val responses = collectEstimateResponses(tasks);

        return mergeResponses(responses);
    }

    private List<CompletableFuture<EstimateResponse>> startEstimateTasks(List<String> stopIds) {
        return stopIds
            .stream()
            .map(stopId -> CompletableFuture.supplyAsync(() -> client.getEstimate(stopId), executorService.getExecutorService()))
            .collect(Collectors.toList());
    }

    private List<EstimateResponse> collectEstimateResponses(List<CompletableFuture<EstimateResponse>> tasks) {
        return tasks
            .stream()
            .map(CompletableFuture::join)
            .collect(Collectors.toList());
    }

    private EstimateResponse mergeResponses(List<EstimateResponse> responses) {
        val lastUpdated = responses
            .stream()
            .map(EstimateResponse::getLastUpdate)
            .findFirst()
            .orElse("");
        val flattenDelays = responses
            .stream()
            .flatMap(response -> response.getDelay().stream())
            .sorted(Comparator.comparing(Delay::getEstimatedTime, Comparator.naturalOrder()))
            .collect(Collectors.toList());

        return new EstimateResponse(lastUpdated, flattenDelays);
    }
}
