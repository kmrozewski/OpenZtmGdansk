package com.opengdansk.announcement.model;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class Announcement {

    private String message;
    private String startDate;
    private String endDate;
}
