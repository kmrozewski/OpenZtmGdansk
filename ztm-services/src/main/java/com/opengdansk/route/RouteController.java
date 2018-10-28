package com.opengdansk.route;

import com.opengdansk.route.service.RouteService;
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
public class RouteController {

    @NonNull
    private final RouteService service;

    @RequestMapping(value = "route/all", method = GET)
    public ResponseEntity getAllRoutes() {
        val response = service.getAllRoutes();

        return new ResponseEntity<>(response, OK);
    }

    @RequestMapping(value = "route/{routeId}", method = GET)
    public ResponseEntity getByRouteId(@PathVariable("routeId") Integer routeId) {
        val response = service.findByRouteId(routeId);

        return new ResponseEntity<>(response, OK);
    }
}
