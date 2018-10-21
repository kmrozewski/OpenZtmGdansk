package com.opengdansk.announcement.service;

import com.opengdansk.announcement.model.StopDisplayMessageResponse;
import com.opengdansk.announcement.model.StopDisplayTable;
import com.opengdansk.announcement.model.StopDisplayTableResponse;
import com.opengdansk.configuration.ZtmApiConfiguration;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class StopDisplayService {

    @NonNull
    private final RestTemplate restTemplate;

    @NonNull
    private final ZtmApiConfiguration configuration;

    public StopDisplayTableResponse getTables() {
        return restTemplate.getForObject(configuration.getStopTableListUrl(), StopDisplayTableResponse.class);
    }

    public Map<Integer, StopDisplayTable> getTablesByStopIds(List<Integer> stopIds) {
        return getTables()
                .getDisplayMap()
                .entrySet()
                .stream()
                .filter(entry -> entry.getValue().getStopIds().stream().anyMatch(stopIds::contains))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public StopDisplayMessageResponse getMessages() {
        return restTemplate.getForObject(configuration.getDisplayMessageListUrl(), StopDisplayMessageResponse.class);
    }
}
