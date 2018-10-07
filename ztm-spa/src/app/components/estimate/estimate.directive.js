(function() {
    'use strict';

    angular
        .module('ztmSpa')
        .directive('estimate', function(EstimateService) {
            return {
                controller: function($scope) {
                    var counter = 0;
                    $scope.delays = [];

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
                        update("", false, true);
                        return EstimateService.getEstimate(stopIds).$promise.then(getEstimateSuccess, getEstimateError);
                    }

                    function getEstimateSuccess(response) {
                        counter = 0;
                        update(response.lastUpdate, false, false);
                        $scope.delays = response.delay;
                    }

                    function getEstimateError() {
                        counter += 1;

                        if (counter <= 3) {
                            getEstimate($scope.stopIds);
                        } else {
                            update("", true, false);
                            $scope.hasError = true;
                        }
                    }

                    function update(lastUpdate, hasError, isLoading) {
                        $scope.$emit('estimateUpdated', {
                            lastUpdate: lastUpdate,
                            hasError: hasError,
                            isLoading: isLoading
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