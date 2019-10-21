package com.opengdansk.search.service;

import com.opengdansk.search.conguration.StopMapBean;
import com.opengdansk.search.model.Stop;
import com.opengdansk.search.model.StopCodeAgg;
import com.opengdansk.search.model.StopNameAgg;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SearchService {

    @NonNull
    private final StopMapBean stopMapBean;

    public Map<String, List<StopCodeAgg>> getAllStops() {
        return stopMapBean.getStopsMap();
    }

    public Map<String, List<Integer>> getAllStopIds() {
        return stopMapBean
                .getStopsMap()
                .entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey,
                        entry -> entry
                                .getValue()
                                .stream()
                                .flatMap(stopCodeAgg -> stopCodeAgg.getStops().stream().map(Stop::getId))
                                .collect(Collectors.toList())));
    }

    public StopNameAgg getStopByName(String stopName) {
        return Optional
                .ofNullable(stopMapBean.getStopsMap().get(stopName))
                .map(codes -> StopNameAgg.builder().name(stopName).codes(sortByStopCode(codes)).build())
                .orElse(StopNameAgg.builder().name(stopName).codes(emptyList()).build());
    }

    private List<StopCodeAgg> sortByStopCode(List<StopCodeAgg> codes) {
        return codes
                .stream()
                .sorted(Comparator.comparing(StopCodeAgg::getCode))
                .collect(Collectors.toList());
    }
}
