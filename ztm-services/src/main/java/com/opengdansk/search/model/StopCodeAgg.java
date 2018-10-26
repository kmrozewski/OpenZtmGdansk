package com.opengdansk.search.model;

import lombok.Data;

import java.util.List;

@Data
public class StopCodeAgg {

    private String code;
    private List<Stop> stops;
}
