package com.opengdansk.search.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StopLocalizatorRequest {

    private StopCoords coords;

    @Min(0)
    @Max(1200)
    private Integer range;

    @Min(5)
    @Max(30)
    private Integer limit;
}
