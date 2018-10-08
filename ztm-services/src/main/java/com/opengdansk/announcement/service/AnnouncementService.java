package com.opengdansk.announcement.service;

import com.opengdansk.announcement.model.StopDisplayMessage;
import com.opengdansk.announcement.model.StopDisplayTable;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AnnouncementService {

    @NonNull
    private final StopDisplayService service;

    private List<StopDisplayTable> getTables() {
        return service.getTables().getDisplays();
    }

    private List<StopDisplayMessage> getMessages() {
        return service.getMessages().getMessages();
    }
}
