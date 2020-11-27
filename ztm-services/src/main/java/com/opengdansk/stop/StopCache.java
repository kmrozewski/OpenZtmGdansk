package com.opengdansk.stop;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.opengdansk.stop.model.Stop;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class StopCache {

    @NonNull
    private StopService stopService;

    @Getter
    private Supplier<Map<String, Map<String, Stop>>> cachedMap;

    @Autowired
    public StopCache(StopService stopService) {
        this.stopService = stopService;
        cachedMap = getInstance();
    }

    private Supplier<Map<String, Map<String, Stop>>> getInstance() {
        val stops = stopService.groupStops();
        log.info("Refreshed cache with {} stops", stops.size());
        return Suppliers.memoizeWithExpiration(() -> stops, 6, TimeUnit.HOURS);
    }
}
