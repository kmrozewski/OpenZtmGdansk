package com.opengdansk.route.service;

import com.opengdansk.route.configuration.RouteMapBean;
import com.opengdansk.route.model.Route;
import lombok.val;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static java.util.Collections.emptyMap;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RouteServiceTest {

    private static final Integer ROUTE_ID = 1234;

    @InjectMocks
    private RouteService service;

    @Mock
    private RouteMapBean routeMapBean;

    @Test
    public void when_noResultsFound_Expect_DummyRoute() {
        when(routeMapBean.getRoutesMap()).thenReturn(emptyMap());

        val route = service.findByRouteId(ROUTE_ID);

        assertThat(route).isEqualTo(Fixtures.DUMMY_ROUTE);
    }

    private static class Fixtures {
        private static final Route DUMMY_ROUTE = Route
                .builder()
                .routeId(ROUTE_ID)
                .shortName(ROUTE_ID.toString())
                .build();
    }
}
