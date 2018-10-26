package com.opengdansk.announcement.service;

import com.opengdansk.announcement.model.StopDisplayMessageResponse;
import com.opengdansk.announcement.model.StopDisplayTable;
import com.opengdansk.announcement.model.StopDisplayTableResponse;
import com.opengdansk.configuration.ZtmApiConfiguration;
import lombok.val;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.opengdansk.search.reader.JsonReader.parseFixture;
import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class StopDisplayServiceTest {

    private static final String STOP_TABLE_LIST_URL = "tables-url";
    private static final String DISPLAY_MESSAGE_LIST_URL = "messages-url";
    private static final Integer FIRST_STOP_ID = 14540;
    private static final Integer SECOND_STOP_ID = 1383;

    private final StopDisplayTableResponse tableResponse =
            parseFixture(this.getClass(), StopDisplayTableResponse.class, "announcement-tables.json");
    private final StopDisplayMessageResponse messageResponse =
            parseFixture(this.getClass(), StopDisplayMessageResponse.class, "announcement-messages.json");

    @InjectMocks
    private StopDisplayService stopDisplayService;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private ZtmApiConfiguration apiConfiguration;

    @Before
    public void setUpMocks() {
        when(apiConfiguration.getStopTableListUrl()).thenReturn(STOP_TABLE_LIST_URL);
        when(apiConfiguration.getDisplayMessageListUrl()).thenReturn(DISPLAY_MESSAGE_LIST_URL);
        when(restTemplate.getForObject(STOP_TABLE_LIST_URL, StopDisplayTableResponse.class))
                .thenReturn(tableResponse);
        when(restTemplate.getForObject(DISPLAY_MESSAGE_LIST_URL, StopDisplayMessageResponse.class))
                .thenReturn(messageResponse);
    }

    @Test
    public void when_ReceivedStopIds_Expect_MapWithTablesThatContainsOnlySelectedStopIds() {
        val filteredTablesByStopIds = getStopIdsFromTableMap(stopDisplayService
                .getTablesByStopIds(Arrays.asList(FIRST_STOP_ID, SECOND_STOP_ID)));

        assertThat(filteredTablesByStopIds).contains(FIRST_STOP_ID, SECOND_STOP_ID);
    }

    @Test
    public void when_ReceivedDuplicatedStopIds_Expect_NoDuplicatesInReturnedMap() {
        val filteredTablesByStopIds = getStopIdsFromTableMap(stopDisplayService
                .getTablesByStopIds(Arrays.asList(SECOND_STOP_ID, SECOND_STOP_ID)));

        assertThat(filteredTablesByStopIds).containsOnlyOnce(SECOND_STOP_ID);
    }

    @Test
    public void when_ReceivedEmptyListWithStopIds_Expect_EmptyMap() {
        val filteredTableMap = stopDisplayService.getTablesByStopIds(emptyList());

        assertThat(filteredTableMap).isEmpty();
    }

    @Test
    public void when_ReturnedMoreThanOneMessagePerDisplayTable_Expect_MessagesToBeMerged() {
        val messages = stopDisplayService.getMessages();
    }

    private List<Integer> getStopIdsFromTableMap(Map<Integer, StopDisplayTable> tableMap) {
        return tableMap
                .entrySet()
                .stream()
                .flatMap(entry -> entry.getValue().getStopIds().stream())
                .collect(Collectors.toList());
    }
}
