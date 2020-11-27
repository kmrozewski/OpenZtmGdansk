package com.opengdansk.estimate.client;

import com.opengdansk.configuration.ZtmApiConfiguration;
import com.opengdansk.estimate.model.EstimateResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EstimateRestfulClient {

    private static final String STOP_ID_QUERY_PARAM = "stopId";
    private static final String DELAYS = "/delays";

    @NonNull
    private final RestTemplate restTemplate;

    @NonNull
    private final ZtmApiConfiguration configuration;

    public EstimateResponse getEstimate(Integer stopId) {
        val url = buildUrl(stopId);

        return restTemplate.getForObject(url, EstimateResponse.class);
    }

    private String buildUrl(Integer stopId) {
        return UriComponentsBuilder
            .fromHttpUrl(configuration.getApiUrl() + DELAYS)
            .queryParam(STOP_ID_QUERY_PARAM, stopId)
            .build()
            .toString();
    }
}
