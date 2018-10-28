package com.opengdansk.route.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Route {

    private Integer routeId;
    private Integer agencyId;

    @JsonProperty("routeShortName")
    private String shortName;

    @JsonProperty("routeLongName")
    private String longName;
}
