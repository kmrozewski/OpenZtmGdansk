package com.opengdansk.location;

import com.opengdansk.configuration.ZtmApiConfiguration;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Collections.emptyMap;
import static java.util.function.Function.identity;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LocationRestfulClient {

    private static final String GPS_POSITIONS = "/gpsPositions";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @NonNull
    private final RestTemplate restTemplate;

    @NonNull
    private final ZtmApiConfiguration configuration;

    public VehicleLocationResponse getLocation() {
        val url = buildUrl();

        LocationResponse response = restTemplate.getForObject(url, LocationResponse.class);

        if (response == null || response.getVehicles() == null) {
            return VehicleLocationResponse
                    .builder()
                    .lastUpdate(LocalDateTime.now().format(FORMATTER))
                    .vehicles(emptyMap())
                    .build();
        }

        Map<Long, VehicleLocation> vehicleLocation = response
                .getVehicles()
                .stream()
                .collect(Collectors.toMap(VehicleLocation::getVehicleId, identity()));

        return VehicleLocationResponse
                .builder()
                .lastUpdate(response.getLastUpdate())
                .vehicles(vehicleLocation)
                .build();
    }

    private String buildUrl() {
        return UriComponentsBuilder
            .fromHttpUrl(configuration.getApiUrl() + GPS_POSITIONS)
            .build()
            .toString();
    }
}
