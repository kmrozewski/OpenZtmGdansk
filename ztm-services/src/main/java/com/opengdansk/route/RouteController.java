package com.opengdansk.route;

import com.opengdansk.route.service.RouteService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@RequestMapping(produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RouteController {

    @NonNull
    private final RouteService service;

    @RequestMapping(value = "route/all", method = GET)
    public ResponseEntity getAllRoutes() {
        val response = service.getAllRoutes();

        return new ResponseEntity<>(response, OK);
    }

    @RequestMapping(value = "route", method = POST)
    public ResponseEntity getRouteByRouteIds(@RequestBody List<Integer> routeIds) {
        val response = service.getRouteMapSubset(routeIds);

        return new ResponseEntity<>(response, OK);
    }
}
