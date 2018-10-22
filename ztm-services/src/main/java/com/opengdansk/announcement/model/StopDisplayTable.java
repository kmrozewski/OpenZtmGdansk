package com.opengdansk.announcement.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StopDisplayTable {

    protected String name;
    protected List<Integer> stopIds;

    public StopDisplayTable(Map<String, Object> entry) {
        if (stopIds == null) {
            this.stopIds = new ArrayList<>();
        }

        this.name = (String) entry.get("name");
        Stream
                .of(
                        (Integer) entry.get("idStop1"),
                        (Integer) entry.get("idStop2"),
                        (Integer) entry.get("idStop3"),
                        (Integer) entry.get("idStop4"))
                .forEach(this::append);
    }

    private void append(Integer stopId) {
        if (stopId != 0) {
            stopIds.add(stopId);
        }
    }
}
