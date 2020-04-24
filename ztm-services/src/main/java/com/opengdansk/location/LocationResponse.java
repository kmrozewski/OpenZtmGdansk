package com.opengdansk.location;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class LocationResponse {

    @JsonProperty("LastUpdateData")
    private String lastUpdate;

    @JsonProperty("Vehicles")
    private List<VehicleLocation> vehicles;
}
