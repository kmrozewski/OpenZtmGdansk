package com.opengdansk.ztmtimetable.estimate;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.List;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.val;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.opengdansk.ztmtimetable.estimate.service.EstimateService;

@Controller
@RequestMapping(produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EstimateController {

    @NonNull
    private final EstimateService service;

    @RequestMapping(value = "estimate/{stopId}", method = GET)
    public ResponseEntity getEstimate(@PathVariable("stopId") String stopId) {
        val response = service.getEstimate(stopId);

        return new ResponseEntity<>(response, OK);
    }

    @RequestMapping(value = "estimate", method = POST)
    public ResponseEntity getEstimates(@RequestBody List<String> stopIds) {
        val response = service.getEstimateTable(stopIds);

        return new ResponseEntity<>(response, OK);
    }
}
