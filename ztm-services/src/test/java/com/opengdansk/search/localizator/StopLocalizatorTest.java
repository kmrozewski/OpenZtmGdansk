package com.opengdansk.search.localizator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class StopLocalizatorTest {

    private static final Double START_LATITUDE = 54.3697176;
    private static final Double END_LATITUDE = 54.3631743;
    private static final Double START_LONGITUDE = 18.6292849;
    private static final Double END_LONGITUDE = 18.6348767;
    private static final Integer EXPECTED_DISTANCE = 812;

    @InjectMocks
    private StopLocalizator stopLocalizator;

    @Test
    public void when_ReceivedCoordinates_Expect_CorrectLocation() {
        Integer distance = stopLocalizator
                .getDistanceInMeters(START_LATITUDE, END_LATITUDE, START_LONGITUDE, END_LONGITUDE);

        assertThat(distance).isEqualTo(EXPECTED_DISTANCE);
    }

    @Test
    public void when_ReceivedTheSameCoordinates_Expect_DistanceToBeEqualZero() {
        Integer distance = stopLocalizator
                .getDistanceInMeters(START_LATITUDE, START_LATITUDE, START_LONGITUDE, START_LONGITUDE);

        assertThat(distance).isEqualTo(0);
    }
}
