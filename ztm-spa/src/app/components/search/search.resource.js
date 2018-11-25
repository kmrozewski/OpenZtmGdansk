(function() {
    'use strict';

    angular
        .module('ztmSpa')
        .service('Search', search);

    /** @ngInject */
    function search($resource, APP_CONFIG) {
        return $resource(APP_CONFIG.API_URL + 'search/:stopName', {stopName: '@stopName'}, {
            getByName: {
                method: 'GET'
            },
            findNearest: {
                method: 'POST',
                url: APP_CONFIG.API_URL + 'search/nearest',
                params: {
                    coords: {
                        lat: '@lat',
                        lon: '@lon'
                    },
                    range: '@range',
                    limit: '@limit'
                },
                isArray: true
            }
        });
    }

})();