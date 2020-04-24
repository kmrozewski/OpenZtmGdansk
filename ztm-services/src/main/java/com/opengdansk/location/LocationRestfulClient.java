package com.opengdansk.location;

import com.opengdansk.configuration.ZtmApiConfiguration;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

import static java.util.Collections.emptyList;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LocationRestfulClient {

    private static final String GPS_POSITIONS = "/gpsPositions";
    @NonNull
    private final RestTemplate restTemplate;

    @NonNull
    private final ZtmApiConfiguration configuration;

    public List<VehicleLocation> getLocation() {
        val url = buildUrl();

        LocationResponse response = restTemplate.getForObject(url, LocationResponse.class);

        if (response == null || response.getVehicles() == null) {
            return emptyList();
        }

        return response.getVehicles();
    }

    private String buildUrl() {
        return UriComponentsBuilder
            .fromHttpUrl(configuration.getApiUrl() + GPS_POSITIONS)
            .build()
            .toString();
    }
}
