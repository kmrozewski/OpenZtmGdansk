package com.opengdansk.search.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StopLocalizatorResult {

    private Integer id;
    private String name;
    private String code;
    private StopCoords coords;
    private Integer distance;
}
