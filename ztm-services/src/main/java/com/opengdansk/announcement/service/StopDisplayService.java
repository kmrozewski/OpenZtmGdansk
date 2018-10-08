package com.opengdansk.announcement.service;

import com.opengdansk.announcement.model.StopDisplayMessageResponse;
import com.opengdansk.announcement.model.StopDisplayTableResponse;
import com.opengdansk.configuration.ZtmApiConfiguration;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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

    public StopDisplayMessageResponse getMessages() {
        return restTemplate.getForObject(configuration.getDisplayMessageListUrl(), StopDisplayMessageResponse.class);
    }
}
