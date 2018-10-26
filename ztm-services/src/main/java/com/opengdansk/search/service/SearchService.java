package com.opengdansk.search.service;

import com.opengdansk.search.conguration.StopMapBean;
import com.opengdansk.search.model.StopCodeAgg;
import com.opengdansk.search.model.StopNameAgg;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.Collections.emptyList;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SearchService {

    @NonNull
    private final StopMapBean stopMapBean;

    public Map<String, List<StopCodeAgg>> getAllStops() {
        return stopMapBean.getStopsMap();
    }

    public StopNameAgg getStopByName(String stopName) {
        return Optional
                .ofNullable(stopMapBean.getStopsMap().get(stopName))
                .map(codes -> StopNameAgg.builder().name(stopName).codes(codes).build())
                .orElse(StopNameAgg.builder().name(stopName).codes(emptyList()).build());
    }
}
