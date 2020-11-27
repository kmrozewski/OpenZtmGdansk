package com.opengdansk.trip.model;

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
public class Trip {

    private static final String REGEX_NUMBER_IN_PARENTHESES = "\\([0-9]+\\)";
    private static final String REGEX_SPECIAL_CHARS = "[\\@\\+]+";
    private static final String REGEX_WORD = "new\\.";

    private String id;
    private Integer routeId;

    private String tripHeadsign;
    private String tripShortName;
    private Integer directionId;


    @JsonProperty("tripHeadsign")
    public void setTripHeadsign(String tripHeadsign) {
        this.tripHeadsign = tripHeadsign
                .replaceAll(REGEX_NUMBER_IN_PARENTHESES, "")
                .replaceAll(REGEX_SPECIAL_CHARS, "")
                .replaceAll(REGEX_WORD, "");
    }
}
