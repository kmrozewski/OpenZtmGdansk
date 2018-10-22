package com.opengdansk.announcement.service;

import com.google.common.collect.ImmutableMap;
import com.opengdansk.announcement.model.Announcement;
import com.opengdansk.announcement.model.StopDisplayMessageResponse;
import com.opengdansk.announcement.model.StopDisplayTable;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.opengdansk.announcement.service.AnnouncementServiceTest.Fixtures.FILTERED_TABLE_MAP;
import static com.opengdansk.reader.FixtureReader.parseFixture;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class AnnouncementServiceTest {

    @InjectMocks
    private AnnouncementService announcementService;

    @Mock
    private StopDisplayService stopDisplayService;

    private StopDisplayMessageResponse messageResponse = parseFixture(
            this.getClass(), StopDisplayMessageResponse.class, "announcement-messages.json");

    @Before
    public void setUpMocks() {
        when(stopDisplayService.getTablesByStopIds(any())).thenReturn(FILTERED_TABLE_MAP);
        when(stopDisplayService.getMessages()).thenReturn(messageResponse);
    }

    @Test
    public void when_ReceivedMessagesWithDuplicates_Expect_DistinctListToBeReturnedByMessageText() {
        List<Announcement> announcements = announcementService.getAnnouncementsByStopIds(Arrays.asList(1383, 14540));

        assertThat(announcements.size()).isEqualTo(3);
        assertThat(announcements
                .stream()
                .collect(Collectors.groupingBy(Announcement::getMessage, Collectors.counting()))
                .values()).containsOnly(1L);
    }

    static class Fixtures {
        static final Map<Integer, StopDisplayTable> FILTERED_TABLE_MAP = ImmutableMap
                .<Integer, StopDisplayTable>builder()
                .put(504, StopDisplayTable
                        .builder()
                        .name("WarneÅ„ska   A/T>centrum")
                        .stopIds(Arrays.asList(14540, 1374))
                        .build())
                .put(505, StopDisplayTable
                        .builder()
                        .name("WarneÅ„ska   A/T>Migowo")
                        .stopIds(Arrays.asList(14539, 1371))
                        .build())
                .put(94, StopDisplayTable
                        .builder()
                        .name("Galeria BaÅ‚tycka   A>Port Lotniczy")
                        .stopIds(singletonList(1383))
                        .build())
                .build();
    }
}
