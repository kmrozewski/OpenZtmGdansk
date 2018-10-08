package com.opengdansk.announcement.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Getter
@Setter
public class StopDisplayTable {

    private Integer displayCode;
    private String name;
    private List<Integer> stopIds;

    @JsonCreator
    public StopDisplayTable(@JsonProperty("displayCode") Integer displayCode,
                            @JsonProperty("name") String name,
                            @JsonProperty("idStop1") Integer stopId1,
                            @JsonProperty("idStop2") Integer stopId2,
                            @JsonProperty("idStop3") Integer stopId3,
                            @JsonProperty("idStop4") Integer stopId4) {

        if (stopIds == null) {
            this.stopIds = new ArrayList<>();
        }

        this.name = name;
        this.displayCode = displayCode;
        Stream.of(stopId1, stopId2, stopId3, stopId4).forEach(this::append);
    }

    private void append(Integer stopId) {
        if (stopId != 0) {
            stopIds.add(stopId);
        }
    }
}
