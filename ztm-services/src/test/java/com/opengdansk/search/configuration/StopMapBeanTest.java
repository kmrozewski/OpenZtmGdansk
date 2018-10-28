package com.opengdansk.search.configuration;

import com.opengdansk.search.conguration.StopMapBean;
import com.opengdansk.search.model.Stop;
import com.opengdansk.search.model.StopCodeAgg;
import lombok.val;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.counting;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class StopMapBeanTest {

    private  static final int NUMBER_OF_STOP_NAMES = 1248;

    @InjectMocks
    private StopMapBean stopMapBean;

    @Test
    public void when_returnedMap_Expect_NumberOfKeysShouldBeEqual() {
        assertThat(stopMapBean.getStopsMap().keySet().size()).isEqualTo(NUMBER_OF_STOP_NAMES);
    }

    @Test
    public void when_returnedMap_Expect_StopIdsShouldBeUnique() {
        val stopIdsCountMap = stopMapBean
                .getStopsMap()
                .entrySet()
                .stream()
                .map(Map.Entry::getValue)
                .flatMap(this::flattenStopCodes)
                .collect(Collectors.groupingBy(identity(), counting()));

        val isEntrySizeMoreThanOne = stopIdsCountMap.entrySet().stream().anyMatch(entry -> entry.getValue() > 1);

        assertThat(isEntrySizeMoreThanOne).isFalse();
    }

    private Stream<Integer> flattenStopCodes(List<StopCodeAgg> stopCodes) {
        return stopCodes
                .stream()
                .map(StopCodeAgg::getStops)
                .flatMap(this::flattenStopIds);
    }

    private Stream<Integer> flattenStopIds(List<Stop> stops) {
        return stops
                .stream()
                .map(Stop::getId);
    }
}
