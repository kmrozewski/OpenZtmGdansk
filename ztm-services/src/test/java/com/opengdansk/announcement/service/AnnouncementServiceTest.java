package com.opengdansk.announcement.service;

import com.opengdansk.announcement.model.StopDisplayMessageResponse;
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

import static com.opengdansk.search.reader.JsonReader.parseFixture;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AnnouncementServiceTest {

    private static final String DISPLAY_MESSAGE_LIST_URL = "messages-url";

    private final StopDisplayMessageResponse messageResponse =
            parseFixture(this.getClass(), StopDisplayMessageResponse.class, "announcement-messages.json");

    @InjectMocks
    private AnnouncementService announcementService;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private ZtmApiConfiguration apiConfiguration;

    @Before
    public void setUpMocks() {
        when(apiConfiguration.getDisplayMessageListUrl()).thenReturn(DISPLAY_MESSAGE_LIST_URL);
        when(restTemplate.getForObject(DISPLAY_MESSAGE_LIST_URL, StopDisplayMessageResponse.class))
                .thenReturn(messageResponse);
    }

    @Test
    public void when_returnedListWithAnnouncements_Expect_ListShouldBeDistinct() {
        val messages = announcementService.getMessagesDistinct();

        assertThat(messages.size()).isEqualTo(3);
        assertThat(messages).containsOnlyElementsOf(Fixtures.EXPECTED_MESSAGES);
    }

    private static class Fixtures {
        private static final List<String> EXPECTED_MESSAGES = Arrays.asList(
                "W dniach 20 - 21 X 2018 (sobota - niedziela) w godz. 08:00-20:00 tramwaje linii 8 i 10 w obu kierunkach kursujÄ… al. ZwyciÄ™stwa z powodu robÃ³t torowych",
                "Åšrednie stÄ™Å¼. pyÅ‚u PM10 = 10,8 ug.  Norma: 300ug. Å›redniodob. Norma WHO: 50ug przez 3 dni. WiÄ™cej: gdansk.pl/powietrze\n",
                "Åšrednie stÄ™Å¼. pyÅ‚u PM10 = 28,8 ug.  Norma: 300ug. Å›redniodob. Norma WHO: 50ug przez 3 dni. WiÄ™cej: gdansk.pl/powietrze\n"
        );
    }
}
