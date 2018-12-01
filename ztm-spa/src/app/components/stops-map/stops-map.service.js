(function() {
    'use strict';

    angular
        .module('ztmSpa')
        .service('StopsMapService', stopsMapService);

    /** @ngInject */
    function stopsMapService() {

        var icon = L.icon({
            iconUrl: 'app/components/stops-map/assets/marker-icon.png',
            iconSize: [25, 41],
            iconRetinaUrl: 'app/components/stops-map/assets/marker-icon-2x.png',
            iconRetinaSize: [50, 82],
            shadowUrl: 'app/components/stops-map/assets/marker-shadow.png',
            shadowSize: [41, 41]
        });

        this.addSearchStopCoordMarkersToFeatureGroup = addSearchStopCoordMarkersToFeatureGroup;
        this.addStopMarkersToFeatureGroup = addStopMarkersToFeatureGroup;

        function addSearchStopCoordMarkersToFeatureGroup(stopCodes, stopName, featureGroup) {
            stopCodes.flatMap(function(stopCode) {
                stopCode.stops.map(function(stop) {
                    getStopCoordsObject(stop.coords, stopCode.code, stopName, featureGroup);
                });
            });
        }

        function addStopMarkersToFeatureGroup(stops, featureGroup) {
            stops.forEach(function(stop) {
                addStopMarkerToFeatureGroup(stop, featureGroup);
            });
        }

        function addStopMarkerToFeatureGroup(stop, featureGroup) {
            L.marker([stop.coords.lat, stop.coords.lon], {icon: icon})
            .bindPopup(stop.name + ' ' + stop.code)
            .addTo(featureGroup);
        }

        function setStop(coords, stopCode, stopName) {
            return {
                coords: coords,
                name: stopName,
                code: stopCode
            };
        }

        function getStopCoordsObject(coords, stopCode, stopName, featureGroup) {
            addStopMarkerToFeatureGroup(setStop(coords, stopCode, stopName), featureGroup);
        }
    }

})();