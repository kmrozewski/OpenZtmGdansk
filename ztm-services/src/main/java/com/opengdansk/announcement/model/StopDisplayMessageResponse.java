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

    private Map<Integer, StopDisplayMessage> messageMap;

    @JsonProperty("displaysMsg")
    private void unpack(List<Map<String, Object>> entryList) {
        messageMap = entryList
                .stream()
                .map(StopDisplayMessageId::new)
                .collect(Collectors.toMap(
                        StopDisplayMessageId::getDisplayCode,
                        StopDisplayMessageId::getStopDisplayMessage,
                        (oldValue, newValue) -> oldValue));
    }
}

