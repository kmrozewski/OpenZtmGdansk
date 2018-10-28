package com.opengdansk.search.conguration;

import com.opengdansk.search.model.StopCodeAgg;
import com.opengdansk.search.model.StopNameAgg;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.opengdansk.search.reader.JsonReader.parseFixtureToCollection;

@Configuration
public class StopMapBean {

    private static final String STOPS_AGGREGATED_BY_NAME_AND_CODE_PATH = "stops_nested.json";
    private final Map<String, List<StopCodeAgg>> stopWithNamesMap = parseFixtureToCollection(
            this.getClass(),
            StopNameAgg.class,
            List.class,
            STOPS_AGGREGATED_BY_NAME_AND_CODE_PATH)
            .stream()
            .collect(Collectors.toMap(StopNameAgg::getName, StopNameAgg::getCodes));

    @Bean
    public Map<String, List<StopCodeAgg>> getStopsMap() {
        return stopWithNamesMap;
    }
}
