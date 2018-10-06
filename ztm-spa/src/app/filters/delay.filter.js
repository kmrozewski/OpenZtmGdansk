(function() {
    'use strict';

    angular
        .module('ztmSpa')
        .filter('delayToTime', delayToTime);

    /** @ngInject */
    function delayToTime() {
        return function(seconds) {
            if (seconds < 0) {
                seconds = seconds * (-1);
            }

            var d = new Date(0, 0, 0, 0, 0, 0, 0);
            d.setSeconds(seconds);

            return d;
        };
    }
})();