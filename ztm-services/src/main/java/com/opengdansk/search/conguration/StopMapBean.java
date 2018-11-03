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
    private Map<String, List<StopCodeAgg>> stopWithNamesMap;

    @Bean
    public Map<String, List<StopCodeAgg>> getStopsMap() {
        if (stopWithNamesMap == null) {
            stopWithNamesMap = mapListToStopsMap();
        }

        return stopWithNamesMap;
    }

    private Map<String, List<StopCodeAgg>> mapListToStopsMap() {
        return readListWithStops()
                .stream()
                .collect(Collectors.toMap(StopNameAgg::getName, StopNameAgg::getCodes));
    }

    private List<StopNameAgg> readListWithStops() {
        return parseFixtureToCollection(
                this.getClass(),
                StopNameAgg.class,
                List.class,
                STOPS_AGGREGATED_BY_NAME_AND_CODE_PATH);
    }
}
