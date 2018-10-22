package com.opengdansk.announcement.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class StopDisplayMessageResponse {

    private List<StopDisplayMessage> messages;

    @JsonProperty("displaysMsg")
    private void unpack(List<Map<String, Object>> entryList) {
        messages = entryList
                .stream()
                .map(StopDisplayMessage::new)
                .collect(Collectors.toList());
    }
}

