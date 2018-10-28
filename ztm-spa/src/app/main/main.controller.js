(function() {
    'use strict';

    angular
        .module('ztmSpa')
        .controller('MainController', MainController);

    /** @ngInject */
    function MainController($scope, $interval, REFRESH_INTERVAL) {
        $scope.lastUpdate = "";
        $scope.hasError = false;
        $scope.isLoading = false;

        $interval(refreshResults, REFRESH_INTERVAL);

        $scope.tabClicked = function(stopId) {
            $scope.$broadcast('estimateTabClicked', stopId);
        };

        $scope.$on('estimateUpdated', function(event, args) {
            $scope.hasError = args.hasError;
            $scope.isLoading = args.isLoading;
            $scope.notFound = args.notFound;
        });

        function refreshResults() {
            $scope.$broadcast('refreshResults', $scope.tabId);
        }
    }
})();