(function() {
    'use strict';

    angular
        .module('ztmSpa')
        .directive('estimate', function(EstimateService, AnnouncementService, REFRESH_INTERVAL) {
            return {
                controller: function($scope) {
                    var counter = 0;
                    $scope.delays = [];
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

                    AnnouncementService.getAnnouncement($scope.stopIds).$promise.then(function(response) {
                        if (response.length >= 0) {
                            $scope.announcements = response;
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
                        update(false, true);
                        return EstimateService.getEstimate(stopIds).$promise.then(getEstimateSuccess, getEstimateError);
                    }

                    function getEstimateSuccess(response) {
                        console.log(response);
                        counter = 0;
                        $scope.lastUpdate = response.lastUpdate;
                        update(false, false);
                        $scope.delays = response.delay;
                    }

                    function getEstimateError() {
                        counter += 1;

                        if (counter <= 3) {
                            getEstimate($scope.stopIds);
                        } else {
                            update(true, false);
                            $scope.hasError = true;
                        }
                    }

                    function update(hasError, isLoading) {
                        $scope.$emit('estimateUpdated', {
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