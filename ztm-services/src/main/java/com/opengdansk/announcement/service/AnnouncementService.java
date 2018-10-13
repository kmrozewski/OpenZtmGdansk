package com.opengdansk.announcement.service;

import com.opengdansk.announcement.model.Announcement;
import com.opengdansk.announcement.model.StopDisplayMessage;
import com.opengdansk.announcement.model.StopDisplayTable;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AnnouncementService {

    @NonNull
    private final StopDisplayService service;

    public List<Announcement> getAnnouncements() {
        val tables = service.getTables().getDisplayMap();
        val messages = service.getMessages().getMessageMap();

        return joinMessagesWithTables(messages, tables);
    }

    private List<Announcement> joinMessagesWithTables(Map<Integer, StopDisplayMessage> messageMap,
                                                      Map<Integer, StopDisplayTable> tableMap) {

        return messageMap
                .entrySet()
                .stream()
                .flatMap(messageEntry -> joinDisplayTables(messageEntry, tableMap))
                .collect(Collectors.toList());
    }

    private Stream<Announcement> joinDisplayTables(Map.Entry<Integer, StopDisplayMessage> messageEntry,
                                                   Map<Integer, StopDisplayTable> tableMap) {

        return tableMap
                .entrySet()
                .stream()
                .filter(tableEntry -> messageEntry.getKey().equals(tableEntry.getKey()))
                .flatMap(tableEntry -> flattenStopIds(tableEntry, messageEntry));
    }

    private Stream<Announcement> flattenStopIds(Map.Entry<Integer, StopDisplayTable> tableEntry,
                                                Map.Entry<Integer, StopDisplayMessage> messageEntry) {

        return tableEntry
                .getValue()
                .getStopIds()
                .stream()
                .map(stopId -> mapToAnnouncement(stopId, messageEntry, tableEntry));
    }


    private Announcement mapToAnnouncement(Integer stopId,
                                           Map.Entry<Integer, StopDisplayMessage> messageEntry,
                                           Map.Entry<Integer, StopDisplayTable> tableEntry) {

        val tableValue = tableEntry.getValue();
        val messageValue = messageEntry.getValue();

        return Announcement
                .builder()
                .displayCode(tableEntry.getKey())
                .displayName(tableValue.getName())
                .stopId(stopId)
                .message(messageValue.getMessage())
                .startDate(messageValue.getStartDate())
                .endDate(messageValue.getEndDate())
                .build();
    }
}
