package com.opengdansk.announcement.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class StopDisplayTableResponse {

    private Map<Integer, StopDisplayTable> displayMap;

    @JsonProperty("displays")
    private void unpack(List<Map<String, Object>> entryList) {
        displayMap = entryList
                .stream()
                .map(StopDisplayTableId::new)
                .collect(Collectors.toMap(
                        StopDisplayTableId::getDisplayCode,
                        StopDisplayTableId::getStopDisplayTable,
                        (oldValue, newValue) -> oldValue));
    }
}
