package com.opengdansk.announcement;

import com.opengdansk.announcement.model.StopDisplayMessageResponse;
import com.opengdansk.announcement.model.StopDisplayTableResponse;
import com.opengdansk.announcement.service.AnnouncementService;
import com.opengdansk.announcement.service.StopDisplayService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
@RequestMapping(produces = APPLICATION_JSON_VALUE, value = "announcement")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AnnouncementController {

    @NonNull
    private StopDisplayService stopDisplayService;

    @NonNull
    private AnnouncementService announcementService;

    @RequestMapping(value = "announcement", method = GET)
    public ResponseEntity getAnnouncements() {
        val response = announcementService.getAnnouncements();

        return new ResponseEntity<>(response, OK);
    }

    @RequestMapping(value = "table", method = GET)
    public ResponseEntity<StopDisplayTableResponse> getTables() {
        val response = stopDisplayService.getTables();

        return new ResponseEntity<>(response, OK);
    }

    @RequestMapping(value = "message", method = GET)
    public ResponseEntity<StopDisplayMessageResponse> getMessages() {
        val response = stopDisplayService.getMessages();

        return new ResponseEntity<>(response, OK);
    }
}
