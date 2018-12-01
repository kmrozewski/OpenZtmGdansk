package com.opengdansk.search.localizator;

import lombok.val;
import org.springframework.stereotype.Component;

import java.util.function.Function;

import static java.lang.Math.*;

@Component
public class StopLocalizator {

    //haversine formula
    //https://www.movable-type.co.uk/scripts/latlong.html
    private static final Integer EARTH_MEAN_RADIUS_METRES = 6371000;
    private static final Function<Double, Double> SUB_FORMULA_C = a -> 2 * atan2(sqrt(a), sqrt(1 - a));
    private static final Function<Double, Double> DISTANCE_FORMULA = c -> EARTH_MEAN_RADIUS_METRES * c;

    public Integer getDistanceInMeters(Double lat1, Double lat2, Double lon1, Double lon2) {
        val a = getSubFormulaA(toRadians(lat1), toRadians(lat2), toRadians(lat2 - lat1), toRadians(lon2 - lon1));

        return SUB_FORMULA_C.andThen(DISTANCE_FORMULA).apply(a).intValue();
    }

    private Double getSubFormulaA(Double phi1, Double phi2, Double deltaPhi, Double deltaLambda) {
        return sin(deltaPhi / 2) * sin(deltaPhi / 2) + cos(phi1) * cos(phi2) * sin(deltaLambda / 2) * sin(deltaLambda / 2);
    }
}
