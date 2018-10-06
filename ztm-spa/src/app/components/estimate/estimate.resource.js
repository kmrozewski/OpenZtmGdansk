(function() {
    'use strict';

    angular
        .module('ztmSpa')
        .service('Estimate', estimate);

    /** @ngInject */
    function estimate($resource, APP_CONFIG) {
        return $resource(APP_CONFIG.API_URL + 'estimate', {}, {
            post: {
                method: 'POST'
            }
        });
    }

})();