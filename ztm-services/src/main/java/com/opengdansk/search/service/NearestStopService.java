package com.opengdansk.search.service;

import com.opengdansk.search.conguration.StopMapBean;
import com.opengdansk.search.localizator.StopLocalizator;
import com.opengdansk.search.model.*;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Comparator.naturalOrder;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class NearestStopService {

    @NonNull
    private final StopMapBean stopMapBean;

    @NonNull
    private final StopLocalizator localizator;

    public List<StopLocalizatorResult> findNearestStopCodes(StopLocalizatorRequest request) {
        return stopMapBean
                .getStopsMap()
                .entrySet()
                .stream()
                .flatMap(entry -> flattenStopCodeAggregates(entry, request))
                .sorted(Comparator.comparing(StopLocalizatorResult::getDistance, naturalOrder()))
                .limit(request.getLimit())
                .collect(Collectors.toList());
    }

    private Stream<StopLocalizatorResult> flattenStopCodeAggregates(Map.Entry<String, List<StopCodeAgg>> entry,
                                                                    StopLocalizatorRequest request) {

        return entry
                .getValue()
                .stream()
                .flatMap(stopCodeAgg -> mapClosestStops(entry.getKey(), stopCodeAgg, request));
    }

    private Stream<StopLocalizatorResult> mapClosestStops(String stopName, StopCodeAgg stopCodeAgg, StopLocalizatorRequest request) {
        return stopCodeAgg
                .getStops()
                .stream()
                .map(stop -> mapLocalizatorResult(stopName, stopCodeAgg.getCode(), stop, request.getCoords()))
                .filter(localizatorResult -> localizatorResult.getDistance() <= request.getRange());
    }

    private StopLocalizatorResult mapLocalizatorResult(String stopName, String stopCode, Stop stop, StopCoords requestCoords) {
        return StopLocalizatorResult
                .builder()
                .id(stop.getId())
                .name(stopName)
                .code(stopCode)
                .coords(stop.getCoords())
                .distance(getSimpleDistance(stop.getCoords(), requestCoords))
                .build();
    }

    private Integer getSimpleDistance(StopCoords stopCoords, StopCoords referenceCoords) {
        return localizator.getDistanceInMeters(
                stopCoords.getLatitude(),
                referenceCoords.getLatitude(),
                stopCoords.getLongitude(),
                referenceCoords.getLongitude());
    }
}
