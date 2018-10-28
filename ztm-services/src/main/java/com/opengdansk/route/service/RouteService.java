package com.opengdansk.route.service;

import com.opengdansk.route.configuration.RouteMapBean;
import com.opengdansk.route.model.Route;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RouteService {

    @NonNull
    private final RouteMapBean routeMapBean;

    public Map<Integer, Route> getAllRoutes() {
        return routeMapBean.getRoutesMap();
    }

    public Map<Integer, Route> getRouteMapSubset(List<Integer> routeIds) {
        return routeIds
                .stream()
                .map(routeId -> routeMapBean.getRoutesMap().get(routeId))
                .filter(Objects::nonNull)
                .collect(toMap(Route::getRouteId, identity()));
    }
}
