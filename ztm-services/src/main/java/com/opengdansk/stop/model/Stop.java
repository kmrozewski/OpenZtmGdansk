package com.opengdansk.stop.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Stop {

    private Integer stopId;
    private String stopCode;
    private String stopName;

    @JsonProperty("stopShortname")
    private String stopShortName;

    private String stopDesc;
    private Double stopLat;
    private Double stopLon;
    private String zoneName;

    @JsonProperty("virtual")
    private Boolean isVirtual;

    @JsonProperty("nonpassenger")
    private Boolean isNonPassenger;

    @JsonProperty("onDemand")
    private Boolean isOnDemand;
}
