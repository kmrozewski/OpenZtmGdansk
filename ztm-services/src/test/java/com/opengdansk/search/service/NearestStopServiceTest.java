package com.opengdansk.search.service;

import com.google.common.collect.ImmutableMap;
import com.opengdansk.search.conguration.StopMapBean;
import com.opengdansk.search.localizator.StopLocalizator;
import com.opengdansk.search.model.Stop;
import com.opengdansk.search.model.StopCodeAgg;
import com.opengdansk.search.model.StopCoords;
import com.opengdansk.search.model.StopLocalizatorRequest;
import lombok.val;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Collections.emptyMap;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class NearestStopServiceTest {

    private static final Integer STOP_DISTANCE_1 = 21;
    private static final Integer STOP_DISTANCE_2 = 30;

    @InjectMocks
    private NearestStopService nearestStopService;

    @Mock
    private StopMapBean stopMapBean;

    @Mock
    private StopLocalizator stopLocalizator;

    @Before
    public void setUpMocks() {
        when(stopMapBean.getStopsMap()).thenReturn(Fixtures.STOP_MAP);
        when(stopLocalizator.getDistanceInMeters(
                eq(Fixtures.STOP_COORDS_1.getLatitude()),
                any(),
                eq(Fixtures.STOP_COORDS_1.getLongitude()),
                any())).thenReturn(STOP_DISTANCE_1);
        when(stopLocalizator.getDistanceInMeters(
                eq(Fixtures.STOP_COORDS_2.getLatitude()),
                any(),
                eq(Fixtures.STOP_COORDS_2.getLongitude()),
                any())).thenReturn(STOP_DISTANCE_2);
    }

    @Test
    public void when_ReceivedCoordinates_Expect_StopsNearbyToBeFound() {
        val result = nearestStopService.findNearestStopCodes(Fixtures.DEFAULT_REQUEST);
        assertThat(result.size()).isEqualTo(90);
    }

    @Test
    public void when_ReceivedCoordinates_Expect_TrimResultList() {
        val resultSize = nearestStopService.findNearestStopCodes(Fixtures.CONSTRAINT_LIMIT_REQUEST).size();

        assertThat(resultSize).isEqualTo(5);
    }

    @Test
    public void when_ReceivedCoordinates_Expect_StopsShouldBeCloserThanSpecifiedRange() {
        val resultSize = nearestStopService.findNearestStopCodes(Fixtures.CONSTRAINT_RANGE_REQUEST).size();

        assertThat(resultSize).isEqualTo(45);
    }

    @Test
    public void when_ReceivedCoordinates_Expect_StopsShouldBeCloserThanSpecifiedRangeAndTrimmed() {
        val resultSize = nearestStopService.findNearestStopCodes(Fixtures.CONSTRAINT_REQUEST).size();

        assertThat(resultSize).isEqualTo(40);
    }

    @Test
    public void when_ReceivedCoordinates_And_StopsListIsEmpty_NoStopsNearbyShouldBeFound() {
        when(stopMapBean.getStopsMap()).thenReturn(emptyMap());

        val resultSize = nearestStopService.findNearestStopCodes(Fixtures.DEFAULT_REQUEST).size();

        assertThat(resultSize).isEqualTo(0);
    }

    private static class Fixtures {
        private static final StopCoords STOP_COORDS_1 = StopCoords
                .builder()
                .latitude(54.1234)
                .longitude(18.4321)
                .build();

        private static final StopCoords STOP_COORDS_2 = StopCoords
                .builder()
                .latitude(54.9999)
                .longitude(18.8888)
                .build();

        private static final StopLocalizatorRequest DEFAULT_REQUEST = getRequest(1000, 100);
        private static final StopLocalizatorRequest CONSTRAINT_REQUEST = getRequest(40, 25);
        private static final StopLocalizatorRequest CONSTRAINT_LIMIT_REQUEST = getRequest(5, 100);
        private static final StopLocalizatorRequest CONSTRAINT_RANGE_REQUEST = getRequest(1000, 25);

        private static final Map<String, List<StopCodeAgg>> STOP_MAP = ImmutableMap
                .<String, List<StopCodeAgg>>builder()
                .put("First Stop Name", getStopCodeAggList())
                .put("Second Stop Name", getStopCodeAggList())
                .put("Third Stop Name", getStopCodeAggList())
                .build();

        private static StopLocalizatorRequest getRequest(Integer limit, Integer range) {
            return StopLocalizatorRequest
                    .builder()
                    .coords(STOP_COORDS_1)
                    .limit(limit)
                    .range(range)
                    .build();
        }

        private static List<StopCodeAgg> getStopCodeAggList() {
            return Stream
                    .of("00", "01", "02")
                    .map(Fixtures::getStopCodeAgg)
                    .collect(Collectors.toList());
        }

        private static StopCodeAgg getStopCodeAgg(String code) {
            return StopCodeAgg
                    .builder()
                    .code(code)
                    .stops(getStopList())
                    .build();
        }

        private static Stop getStop(Integer id) {
            return Stop
                    .builder()
                    .id(id)
                    .coords(id % 2 == 0 ? STOP_COORDS_1 : STOP_COORDS_2)
                    .build();
        }

        private static List<Stop> getStopList() {
            return IntStream
                    .range(0, 10)
                    .mapToObj(Fixtures::getStop)
                    .collect(Collectors.toList());
        }
    }
}
