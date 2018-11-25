(function() {
    'use strict';

    angular
        .module('ztmSpa')
        .directive('stopsMap', function(StopsMapService) {
            return {
                link: function(scope) {
                    scope.$watch('collapsibles', function(newValue, oldValue) {
                        if (newValue.status === true) {
                            plotMap();
                        }

                        // remove map when accordion panel is closed
                        if (newValue.status === false && scope.map) {
                            scope.map.remove();
                        }
                    }, true);

                    function initializeMap() {
                        scope.map = L.map('search-map');
                        var osm = new L.TileLayer('http://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
                            attribution: 'Map data © <a href="http://openstreetmap.org">OpenStreetMap</a> contributors'
                        });
                        scope.map.addLayer(osm);
                    }

                    function plotMap() {
                        initializeMap();
                        var markersGroup = L.featureGroup();
                        StopsMapService.addSearchStopCoordMarkersToFeatureGroup(scope.stopCodes, scope.stopName, markersGroup);
                        scope.map.addLayer(markersGroup);
                        scope.map.fitBounds(markersGroup.getBounds().pad(0.05));
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