package com.opengdansk.trip.service;

import com.opengdansk.trip.configuration.TripMapBean;
import com.opengdansk.trip.model.Trip;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TripService {

    @NonNull
    private final TripMapBean tripMapBean;

    public Map<String, Trip> getAllTrips() {
        return tripMapBean.getTripsMap();
    }

    public Trip findTripById(String tripId) {
        return tripMapBean.getTripsMap().getOrDefault(tripId, new Trip());
    }
}
