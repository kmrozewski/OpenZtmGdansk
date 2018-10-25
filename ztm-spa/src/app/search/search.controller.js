(function() {
    'use strict';

    angular
        .module('ztmSpa')
        .controller('SearchController', SearchController);

    /** @ngInject */
    function SearchController($scope, $state, $stateParams, $http) {
        console.log('search controller loaded');
        $scope.stops = [];

        $http.get('app/search/stops.json').then(function(data) {
            $scope.stops = data.data;
        });

        console.log('state params', $stateParams);

        if ($stateParams.stopName) {
            $scope.stopName = $stateParams.stopName;
            $scope.stopIds = $stateParams.stopIds;
        }

        $scope.searchClicked = function(item) {
            console.log('clicked search', item);
            $state.go('search', {
                'stopIds': item.id,
                'stopName': item.name
            });
        };
    }
})();