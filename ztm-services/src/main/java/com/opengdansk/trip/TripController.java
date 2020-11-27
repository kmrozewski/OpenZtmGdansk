package com.opengdansk.trip;

import com.opengdansk.trip.service.TripService;
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
public class TripController {

    @NonNull
    private final TripService tripService;

    @RequestMapping(value = "trip/all", method = GET)
    public ResponseEntity getAllTrips() {
        return new ResponseEntity<>(tripService.getAllTrips(), OK);
    }

    @RequestMapping(value = "trip/{tripId}", method = GET)
    public ResponseEntity getTripById(@PathVariable("tripId") String tripId) {
        val response = tripService.findTripById(tripId);

        return new ResponseEntity<>(response, OK);
    }
}
