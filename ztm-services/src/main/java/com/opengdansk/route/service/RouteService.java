package com.opengdansk.route.service;

import com.opengdansk.route.configuration.RouteMapBean;
import com.opengdansk.route.model.Route;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RouteService {

    @NonNull
    private final RouteMapBean routeMapBean;

    public Map<Integer, Route> getAllRoutes() {
        return routeMapBean.getRoutesMap();
    }

    public Route findByRouteId(Integer routeId) {
        return routeMapBean
                .getRoutesMap()
                .getOrDefault(routeId, Route.builder().routeId(routeId).shortName(routeId.toString()).build());
    }
}
