(function() {
    'use strict';

    angular
        .module('ztmSpa')
        .directive('stopsMap', function(StopsMapService, leafletData) {
            return {
                link: function(scope) {
                    scope.defaults = {
                        scrollWheelZoom: true
                    };

                    scope.plotMap = function() {
                        var bounds = [StopsMapService.getStopCoordBounds(scope.stopCodes)];
                        scope.markers = StopsMapService.getStopCoordMarkers(scope.stopCodes, scope.stopName);

                        leafletData.getMap().then(function(map) {
                            console.log('map', map);
                            map.fitBounds(bounds, {
                                padding: [20, 20]
                            });
                            map.invalidateSize(true);
                        });
                    };
                },
                restrict: 'E',
                templateUrl: 'app/components/stops-map/stops-map.html',
                scope: {
                    stopCodes: '=',
                    stopName: '='
                }
            };
        })
})();