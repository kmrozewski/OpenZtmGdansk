(function() {
    'use strict';

    angular
        .module('ztmSpa')
        .directive('elemReady', function($parse) {
            return {
                link: function(scope, elem, attrs) {
                    elem.ready(function() {
                        scope.$apply(function() {
                            var func = $parse(attrs.elemReady);
                            func(scope);
                        })
                    })
                },
                restrict: 'A'
            };
        })
})();