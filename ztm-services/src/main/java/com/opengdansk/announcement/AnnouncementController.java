package com.opengdansk.announcement;

import com.opengdansk.announcement.service.AnnouncementService;
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
@RequestMapping(produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AnnouncementController {

    @NonNull
    private AnnouncementService announcementService;

    @RequestMapping(value = "announcement/all", method = GET)
    public ResponseEntity getMessages() {
        val response = announcementService.getMessagesDistinct();

        return new ResponseEntity<>(response, OK);
    }
}
