package com.opengdansk.route.configuration;

import com.opengdansk.route.model.Route;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

import static com.opengdansk.search.reader.JsonReader.parseFixtureToCollection;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

@Configuration
public class RouteMapBean {

    private static final String ROUTES_LIST_PATH = "routes.json";
    private Map<Integer, Route> routeMap;

    @Bean
    public Map<Integer, Route> getRoutesMap() {
        if (routeMap == null) {
            routeMap = mapListToRouteMap();
        }

        return routeMap;
    }

    private Map<Integer, Route> mapListToRouteMap() {
        return readListWithRoutes()
                .stream()
                .collect(toMap(Route::getRouteId, identity()));
    }

    private List<Route> readListWithRoutes() {
        return parseFixtureToCollection(this.getClass(), Route.class, List.class, ROUTES_LIST_PATH);
    }
}
