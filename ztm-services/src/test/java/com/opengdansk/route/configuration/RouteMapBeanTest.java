package com.opengdansk.route.configuration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class RouteMapBeanTest {

    private static final Integer ROUTES_COUNT = 206;

    @InjectMocks
    RouteMapBean routeMapBean;

    @Test
    public void when_returnedMap_Expect_NumberOfRoutesShouldBeEqual() {
        assertThat(routeMapBean.getRoutesMap().keySet().size()).isEqualTo(ROUTES_COUNT);
    }
}
