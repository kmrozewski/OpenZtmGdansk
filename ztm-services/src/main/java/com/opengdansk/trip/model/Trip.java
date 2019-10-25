package com.opengdansk.trip.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Trip {

    private String id;
    private Integer routeId;
    private String tripHeadsign;
    private String tripShortName;
    private Integer directionId;
}
