package com.opengdansk.announcement.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Data;

import java.util.Map;

@Data
public class StopDisplayMessage {

    private Integer displayCode;
    private String message;
    private String startDate;
    private String endDate;

    @JsonCreator
    public StopDisplayMessage(Map<String, Object> entry) {
        this.displayCode = (Integer) entry.get("displayCode");
        this.startDate = (String) entry.get("startDate");
        this.endDate = (String) entry.get("endDate");
        this.message = new StringBuilder()
                .append((String) entry.get("messagePart1"))
                .append((String) entry.get("messagePart2"))
                .toString();
    }
}
