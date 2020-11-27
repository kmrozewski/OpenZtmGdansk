package com.opengdansk.trip.configuration;

import com.opengdansk.trip.model.Trip;
import com.opengdansk.trip.model.TripsBundle;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

import static com.opengdansk.search.reader.JsonReader.parseFixtureToMap;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

@Configuration
public class TripMapBean {

    private static final String TRIP_LIST_PATH = "trips.json";
    private Map<String, Trip> tripMap;

    @Bean
    public Map<String, Trip> getTripsMap() {
        if (tripMap == null) {
            tripMap = mapListToTripMap();
        }

        return tripMap;
    }

    private Map<String, Trip> mapListToTripMap() {
        return readMapWithTripBundles()
                .values()
                .stream()
                .flatMap(bundle -> bundle.getTrips().stream())
                .collect(toMap(Trip::getId, identity(), (oldKey, newKey) -> newKey));
    }

    private Map<String, TripsBundle> readMapWithTripBundles() {
        return parseFixtureToMap(this.getClass(), TripsBundle.class, TRIP_LIST_PATH);
    }
}
