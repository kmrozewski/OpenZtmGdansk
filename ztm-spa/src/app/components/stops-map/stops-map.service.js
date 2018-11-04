(function() {
    'use strict';

    angular
        .module('ztmSpa')
        .service('StopsMapService', stopsMapService);

    /** @ngInject */
    function stopsMapService() {

        // this.getEstimate = getEstimate;
        this.getStopCoordBounds = getStopCoordBounds;
        this.getStopCoordMarkers = getStopCoordMarkers;

        function flattenStopCodes(stopCode) {
            return stopCode.stops.map(getStopCoordsArray);
        }

        function getStopCoordsObject(stop, stopCode, stopName) {
            return {
                key: stopCode.code,
                val: {
                    lat: stop.coords.lat,
                    lng: stop.coords.lon,
                    message: stopName + ' ' + stopCode.code,
                    focus: false,
                    draggable: false
                }
            };
        }

        function getStopCoordsArray(stop) {
            return [stop.coords.lat, stop.coords.lon];
        }

        function getStopCoordBounds(stopCodes) {
            return stopCodes.flatMap(flattenStopCodes);
        }

        function getStopCoordMarkers(stopCodes, stopName) {
            var markerArray = stopCodes.flatMap(function(stopCode) {
                return stopCode.stops.map(function(stop) {
                    return getStopCoordsObject(stop, stopCode, stopName);
                });
            });
            var markerObject = {};

            markerArray.forEach(function(marker) {
                markerObject[marker.key] = marker.val;
            });

            return markerObject;
        }
    }

})();