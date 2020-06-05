package com.opengdansk.stop;

import com.opengdansk.configuration.ZtmApiConfiguration;
import com.opengdansk.stop.model.Stop;
import com.opengdansk.stop.model.StopResponse;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.groupingBy;

@Component
public class StopService {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ZtmApiConfiguration configuration;

    public Map<String, Map<String, List<Stop>>> groupStops() {
        return getStops()
                .stream()
                .filter(stop -> nonNull(stop.getStopName()))
                .filter(stop -> nonNull(stop.getStopCode()))
                .collect(groupingBy(Stop::getStopName, groupingBy(Stop::getStopCode)));
    }

    private List<Stop> getStops() {
        val url = buildUrl();

        ParameterizedTypeReference<Map<String, StopResponse>> responseType = new ParameterizedTypeReference<Map<String, StopResponse>>() {};
        val response = restTemplate.exchange(url, HttpMethod.GET, null, responseType);
        val today = LocalDateTime.now().format(FORMATTER);

        StopResponse stopResponse = Optional.ofNullable(response)
                .map(ResponseEntity::getBody)
                .map(this::getStopResponse)
                .orElse(StopResponse.builder().lastUpdate(today).stops(emptyList()).build());

        return stopResponse.getStops();
    }

    private StopResponse getStopResponse(Map<String, StopResponse> response) {
        val today = LocalDateTime.now().format(FORMATTER);

        return response.get(today);
    }

    private String buildUrl() {
        return UriComponentsBuilder
                .fromHttpUrl(configuration.getStopTableListUrl())
                .build()
                .toString();
    }
}
