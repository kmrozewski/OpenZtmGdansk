package com.opengdansk.estimate.service;

import com.opengdansk.configuration.ZtmApiConfiguration;
import com.opengdansk.estimate.client.EstimateRestfulClient;
import com.opengdansk.estimate.model.Delay;
import com.opengdansk.estimate.model.EstimateResponse;
import lombok.val;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.concurrent.ExecutorService;

import static com.opengdansk.search.reader.JsonReader.parseFixture;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EstimateServiceTest {

    private static final String FIRST_STOP_PATH = "stop-1849.json";
    private static final String SECOND_STOP_PATH = "stop-1371.json";
    private static final String MERGED_STOPS_PATH = "merged-stops.json";
    private static final Integer FIRST_STOP_ID = 1849;
    private static final Integer SECOND_STOP_ID = 1371;
    private static final Integer EMPTY_STOP_ID = 2137;

    @InjectMocks
    private EstimateService estimateService;

    @Mock
    private EstimateRestfulClient restfulClient;

    @Mock
    private ExecutorServiceProvider executorServiceProvider;

    @Mock
    private ZtmApiConfiguration apiConfiguration;

    private EstimateResponse firstResponse;
    private EstimateResponse secondResponse;
    private EstimateResponse mergedResponse;

    @Before
    public void setUp() {
        firstResponse = parseFixture(this.getClass(), EstimateResponse.class, FIRST_STOP_PATH);
        secondResponse = parseFixture(this.getClass(), EstimateResponse.class, SECOND_STOP_PATH);
        mergedResponse = parseFixture(this.getClass(), EstimateResponse.class, MERGED_STOPS_PATH);

        when(apiConfiguration.getExecutorPoolCount()).thenReturn(10);
        ExecutorService executorService = new ExecutorServiceProvider(apiConfiguration).getExecutorService();
        when(executorServiceProvider.getExecutorService()).thenReturn(executorService);
        when(restfulClient.getEstimate(FIRST_STOP_ID)).thenReturn(firstResponse);
        when(restfulClient.getEstimate(SECOND_STOP_ID)).thenReturn(secondResponse);
        when(restfulClient.getEstimate(EMPTY_STOP_ID)).thenReturn(Fixtures.EMPTY_RECORD);
    }


    @Test
    public void when_returnedResponsesFromMultipleStops_Expect_CorrectMergedResponse() {
        val actual = estimateService.getEstimateTable(Arrays.asList(FIRST_STOP_ID, SECOND_STOP_ID));

        assertThat(actual.getDelay()).isEqualTo(mergedResponse.getDelay());
    }

    @Test
    public void when_returnedResponsesFromMultipleStops_Expect_CorrectOrderByEstimatedTimeOfArrival() {
        val actual = estimateService.getEstimateTable(Arrays.asList(FIRST_STOP_ID, SECOND_STOP_ID));

        assertThat(actual.getDelay()).isSortedAccordingTo(Comparator.comparing(Delay::getEstimatedTime));
    }

    @Test
    public void when_returnedResponsesFromMultipleStops_With_EmptyList_Expect_CorrectMergedResponse() {
        val actual = estimateService.getEstimateTable(Arrays
                .asList(FIRST_STOP_ID, SECOND_STOP_ID, EMPTY_STOP_ID));

        assertThat(actual.getDelay()).isEqualTo(mergedResponse.getDelay());
    }

    @Test
    public void when_returnedNoData_Expect_EmptyListWithEstimates() {
        val actual = estimateService.getEstimateTable(singletonList(EMPTY_STOP_ID));

        assertThat(actual.getDelay()).isEmpty();
    }

    @Test
    public void when_returnedMultipleResponses_With_DifferentUpdateTimestamp_Expect_First() {
        val actual = estimateService.getEstimateTable(Arrays.asList(FIRST_STOP_ID, SECOND_STOP_ID));

        assertThat(actual.getLastUpdate()).isEqualTo(firstResponse.getLastUpdate());
    }

    private static class Fixtures {
        private static final EstimateResponse EMPTY_RECORD = EstimateResponse
                .builder()
                .lastUpdate("2005-04-02 21:37:00")
                .delay(new ArrayList<>())
                .build();
    }
}
