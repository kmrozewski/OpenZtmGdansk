(function() {
    'use strict';

    angular
        .module('ztmSpa')
        .directive('route', function(RouteService) {
            return {
                restrict: 'E',
                templateUrl: 'app/components/route/route.html',
                scope: {
                    delay: '='
                },
                link: function(scope) {
                    scope.routeName = scope.delay.routeId;
                    scope.headSign = scope.delay.headsign;

                    RouteService.getById(scope.delay.routeId).$promise.then(function(response) {
                        scope.routeName = response.routeShortName;
                    })
                }
            };
        })
})();