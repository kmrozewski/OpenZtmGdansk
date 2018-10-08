package com.opengdansk.announcement.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Announcement {

    private String displayCode;
    private String displayName;
    private String stopId;
    private String message;
    private String startDate;
    private String endDate;
}
