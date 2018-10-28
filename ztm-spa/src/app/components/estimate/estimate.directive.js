(function() {
    'use strict';

    angular
        .module('ztmSpa')
        .directive('estimate', function(EstimateService, REFRESH_INTERVAL) {
            return {
                controller: function($scope) {
                    var counter = 0;
                    $scope.delays = [];
                    $scope.messages = [];
                    $scope.lastUpdate = "";
                    $scope.updateInterval = REFRESH_INTERVAL / 1000;

                    $scope.$on('estimateTabClicked', function(event, args) {
                        if (angular.isArray(args) && isInDirectiveStopIds(args)) {
                            getEstimate(args);
                        }
                    });

                    $scope.$on('refreshResults', function(event, args) {
                        if ($scope.tabId === args) {
                            getEstimate($scope.stopIds);
                        }
                    })

                    if ($scope.isMain) {
                        getEstimate($scope.stopIds);
                    }

                    function isInDirectiveStopIds(stopIds) {
                        return stopIds.every(function(element) {
                            return $scope.stopIds.includes(element);
                        })
                    }

                    function getEstimate(stopIds) {
                        update(false, true, false);
                        return EstimateService.getEstimate(stopIds).$promise.then(getEstimateSuccess, getEstimateError);
                    }

                    function getEstimateSuccess(response) {
                        counter = 0;
                        $scope.lastUpdate = response.lastUpdate;
                        update(false, false, response.delay.length === 0);
                        $scope.delays = response.delay;
                    }

                    function getEstimateError() {
                        counter += 1;

                        if (counter <= 3) {
                            getEstimate($scope.stopIds);
                        } else {
                            update(true, false, true);
                            $scope.hasError = true;
                        }
                    }

                    function update(hasError, isLoading, notFound) {
                        $scope.$emit('estimateUpdated', {
                            hasError: hasError,
                            isLoading: isLoading,
                            notFound: notFound
                        });
                    }
                },
                restrict: 'E',
                replace: true,
                templateUrl: 'app/components/estimate/estimate.html',
                scope: {
                    isMain: '=',
                    stopIds: '=',
                    tabId: '='
                }
            };
        })
})();