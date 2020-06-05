package com.opengdansk.location;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VehicleLocationResponse {

    private String lastUpdate;
    private Map<Long, VehicleLocation> vehicles;
}
