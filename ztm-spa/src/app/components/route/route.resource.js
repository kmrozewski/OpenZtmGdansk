(function() {
    'use strict';

    angular
        .module('ztmSpa')
        .service('Route', route);

    /** @ngInject */
    function route($resource, APP_CONFIG) {
        return $resource(APP_CONFIG.API_URL + 'route/:routeId', {routeId: '@routeId'}, {
            getById: {
                method: 'GET'
            }
        });
    }

})();