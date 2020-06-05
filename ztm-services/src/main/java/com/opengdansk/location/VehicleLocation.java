package com.opengdansk.location;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class VehicleLocation {

    @JsonProperty("VehicleId")
    private Long vehicleId;

    @JsonProperty("VehicleCode")
    private String vehicleCode;

    @JsonProperty("VehicleService")
    private String vehicleService;

    @JsonProperty("Lat")
    private Double lat;

    @JsonProperty("Lon")
    private Double lon;

    @JsonProperty("Speed")
    private Integer speed;

    @JsonProperty("GPSQuality")
    private Integer gpsQuality;

    @JsonProperty("Line")
    private String line;

    // API docs says it's tripId
    @JsonProperty("Route")
    private String tripId;

    @JsonProperty("Delay")
    private Integer delay;
}
