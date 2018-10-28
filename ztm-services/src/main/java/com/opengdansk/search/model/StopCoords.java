package com.opengdansk.search.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class StopCoords {

    @JsonProperty("lat")
    private Double latitude;

    @JsonProperty("lon")
    private Double longitude;
}
