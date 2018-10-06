(function() {
    'use strict';

    angular
        .module('ztmSpa')
        .filter('delayText', delayText);

    /** @ngInject */
    function delayText() {
        return function(seconds) {
            if (seconds <= 0) {
                return "Przyśpieszony o";
            }

            return "Opóźniony o";
        };
    }
})();