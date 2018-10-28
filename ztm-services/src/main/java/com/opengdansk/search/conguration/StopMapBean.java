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

    @Bean
    public Map<String, List<StopCodeAgg>> getStopsMap() {
        List<StopNameAgg> stopsWithNames = parseFixtureToCollection(
                this.getClass(),
                StopNameAgg.class,
                List.class,
                STOPS_AGGREGATED_BY_NAME_AND_CODE_PATH);

        return transformStopsListToMap(stopsWithNames);
    }

    private Map<String, List<StopCodeAgg>> transformStopsListToMap(List<StopNameAgg> stopsWithNames) {
        return stopsWithNames
                .stream()
                .collect(Collectors.toMap(StopNameAgg::getName, StopNameAgg::getCodes));
    }
}
