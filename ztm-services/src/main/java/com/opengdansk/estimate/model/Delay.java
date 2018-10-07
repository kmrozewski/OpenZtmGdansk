package com.opengdansk.estimate.model;

import lombok.Data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Delay {

    private String id;
    private Integer delayInSeconds;
    private String estimatedTime;
    private String headsign;
    private Integer routeId;
    private Integer tripId;
    private String status;
    private String theoreticalTime;
    private String timestamp;
    private Long trip;
    private Integer vehicleCode;
    private Long vehicleId;
}
