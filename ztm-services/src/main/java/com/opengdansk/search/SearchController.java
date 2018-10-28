package com.opengdansk.search;


import com.opengdansk.search.service.SearchService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
@RequestMapping(produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SearchController {

    @NonNull
    private final SearchService service;

    @RequestMapping(value = "search/all", method = GET)
    public ResponseEntity getAllStops() {
        val response = service.getAllStops();

        return new ResponseEntity<>(response, OK);
    }

    @RequestMapping(value = "search/{stopName}", method = GET)
    public ResponseEntity getStopsByName(@PathVariable("stopName") String stopName) {
        val response = service.getStopByName(stopName);

        return new ResponseEntity<>(response, OK);
    }
}
