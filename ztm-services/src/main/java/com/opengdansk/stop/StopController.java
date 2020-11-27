package com.opengdansk.stop;

import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import static java.util.Collections.emptyMap;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
@RequestMapping(value = "stop", produces = APPLICATION_JSON_VALUE)
public class StopController {

    @Autowired
    private StopCache stopCache;

    @RequestMapping(method = GET)
    public ResponseEntity getCachedStops() {
        val response = stopCache.getCachedMap().get();

        return new ResponseEntity<>(response, OK);
    }

    @RequestMapping(value = "{stopName}", method = GET)
    public ResponseEntity getCachedStopByName(@PathVariable("stopName") String stopName) {
        val response = stopCache.getCachedMap().get().getOrDefault(stopName, emptyMap());

        return new ResponseEntity<>(response, OK);
    }

    @RequestMapping(value = "names", method = GET)
    public ResponseEntity getStopNames() {
        val response = stopCache.getCachedMap().get().keySet().toArray();

        return new ResponseEntity<>(response, OK);
    }
}
