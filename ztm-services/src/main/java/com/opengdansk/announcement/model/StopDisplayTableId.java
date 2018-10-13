package com.opengdansk.announcement.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class StopDisplayTableId {

    private Integer displayCode;
    private StopDisplayTable stopDisplayTable;

    public StopDisplayTableId(Map<String, Object> entry) {
        this.stopDisplayTable = new StopDisplayTable(entry);
        this.displayCode = (Integer) entry.get("displayCode");
    }
}
