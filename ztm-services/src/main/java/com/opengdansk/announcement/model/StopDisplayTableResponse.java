package com.opengdansk.announcement.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class StopDisplayTableResponse {

    private List<StopDisplayTable> displays;
}
