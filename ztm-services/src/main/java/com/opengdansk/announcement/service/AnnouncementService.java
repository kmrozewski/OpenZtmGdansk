package com.opengdansk.announcement.service;

import com.opengdansk.announcement.model.StopDisplayMessage;
import com.opengdansk.announcement.model.StopDisplayMessageResponse;
import com.opengdansk.configuration.ZtmApiConfiguration;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AnnouncementService {

    private static final String REPLACEMENT = " ";
    private static final String WHITESPACES = " +";

    @NonNull
    private final RestTemplate restTemplate;

    @NonNull
    private final ZtmApiConfiguration configuration;

    public List<String> getMessagesDistinct() {
        return Objects.requireNonNull(restTemplate
                .getForObject(configuration.getDisplayMessageListUrl(), StopDisplayMessageResponse.class))
                .getMessages()
                .stream()
                .map(StopDisplayMessage::getMessage)
                .map(this::trimWhitespaces)
                .distinct()
                .collect(Collectors.toList());
    }

    private String trimWhitespaces(String message) {
        return message.replaceAll(WHITESPACES, REPLACEMENT);
    }
}
