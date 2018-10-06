package com.opengdansk.ztmtimetable.estimate.client;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.val;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.opengdansk.ztmtimetable.estimate.configuration.ZtmApiConfiguration;
import com.opengdansk.ztmtimetable.estimate.model.EstimateResponse;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RestfulClient {

    private static final String STOP_ID_QUERY_PARAM = "stopId";

    @NonNull
    private final RestTemplate restTemplate;

    @NonNull
    private final ZtmApiConfiguration configuration;

    public EstimateResponse getEstimate(String stopId) {
        val url = buildUrl(stopId);

        return restTemplate.getForObject(url, EstimateResponse.class);
    }

    private String buildUrl(String stopId) {
        return UriComponentsBuilder
            .fromHttpUrl(configuration.getEstimateServiceUrl())
            .queryParam(STOP_ID_QUERY_PARAM, stopId)
            .build()
            .toString();
    }
}
