package com.opengdansk.announcement.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StopDisplayMessage {

    private String displayCode;
    private String displayName;
    private String message;
    private String startDate;
    private String endDate;
    private String configurationDate;
    private Integer msgType;

    @JsonCreator
    public StopDisplayMessage(@JsonProperty("displayCode") String displayCode,
                              @JsonProperty("displayName") String displayName,
                              @JsonProperty("messagePart1") String messagePart1,
                              @JsonProperty("messagePart2") String messagePart2,
                              @JsonProperty("startDate") String startDate,
                              @JsonProperty("endDate") String endDate,
                              @JsonProperty("configurationDate") String configurationDate,
                              @JsonProperty("msgType") Integer msgType) {

        this.displayCode = displayCode;
        this.displayName = displayName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.configurationDate = configurationDate;
        this.msgType = msgType;

        this.message = new StringBuilder()
                .append(messagePart1)
                .append(messagePart2)
                .toString();
    }
}
