(function() {
    'use strict';

    angular
        .module('ztmSpa')
        .controller('MainController', MainController);

    /** @ngInject */
    function MainController($scope) {
        $scope.lastUpdate = "";
        $scope.hasError = false;
        $scope.isLoading = false;

        $scope.tabClicked = function(stopId) {
            $scope.$broadcast('estimateTabClicked', stopId);
        };

        $scope.$on('estimateUpdated', function(event, args) {
            $scope.lastUpdate = args.lastUpdate;
            $scope.hasError = args.hasError;
            $scope.isLoading = args.isLoading;
        });
    }
})();