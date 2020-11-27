package com.opengdansk.search;


import com.opengdansk.search.model.StopLocalizatorRequest;
import com.opengdansk.search.service.NearestStopService;
import com.opengdansk.search.service.SearchService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@RequestMapping(value = "search", produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SearchController {

    @NonNull
    private final SearchService searchService;

    @NonNull
    private final NearestStopService nearestStopService;

    @RequestMapping(value = "all", method = GET)
    public ResponseEntity getAllStops() {
        val response = searchService.getAllStops();

        return new ResponseEntity<>(response, OK);
    }

    @RequestMapping(value = "ids", method = GET)
    public ResponseEntity getAllStopWithIds() {
        val response = searchService.getAllStopIds();

        return new ResponseEntity<>(response, OK);
    }

    @RequestMapping(value = "{stopName}", method = GET)
    public ResponseEntity getStopsByName(@PathVariable("stopName") String stopName) {
        val response = searchService.getStopByName(stopName);

        return new ResponseEntity<>(response, OK);
    }

    @RequestMapping(value = "{stopName}/{stopCode}", method = GET)
    public ResponseEntity getStopIdsByNameAndCode(@PathVariable("stopName") String stopName,
                                                  @PathVariable("stopCode") String stopCode) {

        val response = searchService.getStopIdsByNameAndCode(stopName, stopCode);

        return new ResponseEntity<>(response, OK);
    }


    @RequestMapping(value = "nearest", method = POST, consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity getNearestStops(@RequestBody @Valid StopLocalizatorRequest request) {
        val response = nearestStopService.findNearestStopCodes(request);

        return new ResponseEntity<>(response, OK);
    }
}
