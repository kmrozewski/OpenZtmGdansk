package com.opengdansk.stop.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StopResponse {

    private String lastUpdate;
    private List<Stop> stops;
}
