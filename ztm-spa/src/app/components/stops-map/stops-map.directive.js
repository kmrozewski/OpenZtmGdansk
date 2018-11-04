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

                    scope.$watch('collapsibles', function(newValue, oldValue) {
                        if (newValue.status === true) {
                            plotMap();
                        }
                    }, true);

                    function plotMap() {
                        console.log('plotMap()');

                        var bounds = [StopsMapService.getStopCoordBounds(scope.stopCodes)];
                        scope.markers = StopsMapService.getStopCoordMarkers(scope.stopCodes, scope.stopName);

                        leafletData.getMap().then(function(map) {
                            console.log('map', map);
                            map.fitBounds(bounds, {
                                padding: [30, 30]
                            });
                            map.invalidateSize(true);
                        });
                    };
                },
                restrict: 'E',
                templateUrl: 'app/components/stops-map/stops-map.html',
                scope: {
                    stopCodes: '=',
                    stopName: '=',
                    collapsibles: '='
                }
            };
        })
})();