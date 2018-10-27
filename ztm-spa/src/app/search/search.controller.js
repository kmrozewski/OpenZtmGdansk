(function() {
    'use strict';

    angular
        .module('ztmSpa')
        .controller('SearchController', SearchController);

    /** @ngInject */
    function SearchController($scope, $state, $stateParams, $http, $interval, SearchService, REFRESH_INTERVAL) {
        $scope.lastUpdate = "";
        $scope.hasError = false;
        $scope.isLoading = false;
        $scope.stops = [];
        $scope.stopCodes = [];
        $scope.stopIds = [];

        $http.get('app/search/stops.json').then(function(data) {
            $scope.stops = data.data;
        });

        $interval(refreshResults, REFRESH_INTERVAL);

        if ($stateParams.stopName) {
            $scope.stopName = $stateParams.stopName;
        }

        if ($scope.stopName) {
            SearchService.searchByName($scope.stopName).$promise.then(function(response) {
                $scope.stopCodes = response.codes;
                $scope.stopIds = getStopIds();
            });
        }

        $scope.searchClicked = function(item) {
            $state.go('search', {
                'stopName': item
            });
        };

        $scope.tabClicked = function(index) {
            $scope.$broadcast('estimateTabClicked', $scope.stopIds[index]);
        };

        $scope.$on('estimateUpdated', function(event, args) {
            $scope.hasError = args.hasError;
            $scope.isLoading = args.isLoading;
        });

        function getStopIds() {
            return $scope.stopCodes.map(function(stopCode) { return stopCode.stops.map(function(stop){ return stop.id; }) });
        }

        function refreshResults() {
            $scope.$broadcast('refreshResults', $scope.tabId);
        }
    }
})();