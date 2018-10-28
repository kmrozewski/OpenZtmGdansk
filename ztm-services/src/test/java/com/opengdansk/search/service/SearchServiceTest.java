package com.opengdansk.search.service;

import com.google.common.collect.ImmutableMap;
import com.opengdansk.search.conguration.StopMapBean;
import com.opengdansk.search.model.StopCodeAgg;
import lombok.val;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SearchServiceTest {

    private static final String CORRECT_STOP_NAME = "Pomorska";
    private static final String INCORRECT_STOP_NAME = "Pomo";

    @InjectMocks
    private SearchService service;

    @Mock
    private StopMapBean stopMapBean;

    @Test
    public void when_returnedMapWithStopCodes_Expect_StopCodesAreSorted() {
        when(stopMapBean.getStopsMap()).thenReturn(Fixtures.STOP_FOUND_MAP);

        val stopName = service.getStopByName(CORRECT_STOP_NAME);

        assertThat(stopName.getName()).isEqualTo(CORRECT_STOP_NAME);
        assertThat(stopName.getCodes().size()).isEqualTo(4);
        assertThat(stopName.getCodes().stream().map(StopCodeAgg::getCode).collect(Collectors.toList())).isSorted();
    }

    @Test
    public void when_noResultsFound_Expect_ListWithCodes() {
        when(stopMapBean.getStopsMap()).thenReturn(Fixtures.STOP_NOT_FOUND);

        val stopName = service.getStopByName(INCORRECT_STOP_NAME);

        assertThat(stopName.getName()).isEqualTo(INCORRECT_STOP_NAME);
        assertThat(stopName.getCodes().size()).isEqualTo(0);
    }

    private static class Fixtures {
        private static final List<StopCodeAgg> STOP_CODES = Stream
                .of("04", "00", "02", "01")
                .map(stopCode -> StopCodeAgg.builder().code(stopCode).stops(emptyList()).build())
                .collect(Collectors.toList());

        private static final Map<String, List<StopCodeAgg>> STOP_FOUND_MAP = ImmutableMap
                .<String, List<StopCodeAgg>>builder()
                .put(CORRECT_STOP_NAME, STOP_CODES)
                .build();

        static final Map<String, List<StopCodeAgg>> STOP_NOT_FOUND = ImmutableMap
                .<String, List<StopCodeAgg>>builder()
                .put(INCORRECT_STOP_NAME, emptyList())
                .build();
    }
}
