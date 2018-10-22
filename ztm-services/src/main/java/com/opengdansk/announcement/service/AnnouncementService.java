package com.opengdansk.announcement.service;

import com.opengdansk.announcement.model.Announcement;
import com.opengdansk.announcement.model.StopDisplayMessage;
import com.opengdansk.announcement.model.StopDisplayTable;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AnnouncementService {

    @NonNull
    private final StopDisplayService service;

    public List<Announcement> getAnnouncements() {
        return mergeToAnnouncements(service.getTables().getDisplayMap());
    }

    public List<Announcement> getAnnouncementsByStopIds(List<Integer> stopIds) {
        return mergeToAnnouncements(service.getTablesByStopIds(stopIds));
    }

    private List<Announcement> mergeToAnnouncements(Map<Integer, StopDisplayTable> tableMap) {
        List<StopDisplayMessage> messages = service.getMessages().getMessages();

        return removeDuplicatesByMessageText(joinMessagesWithTables(messages, tableMap));
    }

    private Map<String, Announcement> joinMessagesWithTables(List<StopDisplayMessage> messages,
                                                             Map<Integer, StopDisplayTable> tableMap) {
        return messages
                .stream()
                .flatMap(message -> findStopIdsForMessage(message, tableMap))
                .collect(Collectors.toMap(Announcement::getMessage,
                        Function.identity(),
                        (oldValue, newValue) -> oldValue));
    }

    private List<Announcement> removeDuplicatesByMessageText(Map<String, Announcement> announcementMap) {
        return announcementMap
                .entrySet()
                .stream()
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
    }

    private Stream<Announcement> findStopIdsForMessage(StopDisplayMessage message,
                                                       Map<Integer, StopDisplayTable> tableMap) {

        return Optional.ofNullable(tableMap.get(message.getDisplayCode()))
                .map(table -> flattenStopIds(table, message))
                .orElse(Stream.empty());
    }

    private Stream<Announcement> flattenStopIds(StopDisplayTable table, StopDisplayMessage message) {
        return table
                .getStopIds()
                .stream()
                .map(stopId -> mapToAnnouncement(message));
    }

    private Announcement mapToAnnouncement(StopDisplayMessage message) {
        return Announcement
                .builder()
                .message(message.getMessage())
                .startDate(message.getStartDate())
                .endDate(message.getEndDate())
                .build();
    }
}
