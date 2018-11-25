(function() {
    'use strict';
    angular
        .module('ztmSpa')
        .controller('NearestController', NearestController);
    /** @ngInject */
    function NearestController($scope, $interval, SearchService, StopsMapService, REFRESH_INTERVAL) {
        $scope.selected = {
            limit: 15,
            range: 500
        };

        $scope.collapsibles = {
            settings: false,
            map: true
        };

        $scope.options = {
            limit: [5, 15, 30],
            range: [{
                key: '300m',
                val: 300
            }, {
                key: '500m',
                val: 500
            }, {
                key: '800m',
                val: 800
            }, {
                key: '1200m',
                val: 1200
            }]
        };

        $scope.update = function() {
            $scope.map.remove();
            $scope.elemReady();
            $scope.collapsibles.settings = false;
            $scope.collapsibles.map = true;
        };

        $scope.lastUpdate = "";
        $scope.hasError = false;
        $scope.isLoading = false;
        $scope.stops = [];
        $scope.stopIds = [];
        $scope.map = {};

        $scope.elemReady = function() {
            $scope.map = L.map('map');
            var osm = L.tileLayer('https://{s}.tile.osm.org/{z}/{x}/{y}.png', {
                attribution: '&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors'
            })
            $scope.map.addLayer(osm);
            $scope.map.locate({
                setView: true
            });

            $scope.map.on('locationfound', locationFound);
            $scope.map.on('locationerror', locationError);
        }
        $interval(refreshResults, REFRESH_INTERVAL);
        $scope.tabClicked = function(index) {
            $scope.$broadcast('estimateTabClicked', index);
        };
        $scope.$on('estimateUpdated', function(event, args) {
            $scope.hasError = args.hasError;
            $scope.isLoading = args.isLoading;
            $scope.notFound = args.notFound;
        });

        function refreshResults() {
            $scope.$broadcast('refreshResults', $scope.tabId);
        }

        function locationFound(event) {
            L.circle(event.latlng, event.accuracy).addTo($scope.map);
            SearchService.findNearest(prepareRequest(event.latitude, event.longitude)).$promise.then(function(response) {
                $scope.stops = response;
                $scope.stopIds = getStopIds();
                plotNearestStops();
            });
        }

        function locationError(event) {
            console.log('locaiton error', event)
        }

        function plotNearestStops() {
            var markersGroup = L.featureGroup();
            StopsMapService.addStopMarkersToFeatureGroup($scope.stops, markersGroup);
            $scope.map.addLayer(markersGroup);
            $scope.map.fitBounds(markersGroup.getBounds().pad(0.05));
        }

        function refreshResults() {
            $scope.$broadcast('refreshResults', $scope.tabId);
        }

        function prepareRequest(lat, lon) {
            return {
                coords: {
                    lat: lat,
                    lon: lon
                },
                range: $scope.selected.range,
                limit: $scope.selected.limit
            };
        }

        function getStopIds() {
            return $scope.stops.map(function(stop) {
                return [stop.id];
            });
        }
    }
})();