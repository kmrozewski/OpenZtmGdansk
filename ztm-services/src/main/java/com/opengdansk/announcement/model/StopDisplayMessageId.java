package com.opengdansk.announcement.model;


import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class StopDisplayMessageId {

    private Integer displayCode;
    private StopDisplayMessage stopDisplayMessage;

    public StopDisplayMessageId(Map<String, Object> entry) {
        this.stopDisplayMessage = new StopDisplayMessage(entry);
        this.displayCode = (Integer) entry.get("displayCode");
    }
}
