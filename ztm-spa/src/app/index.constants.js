(function() {
    'use strict';

    angular
        .module('ztmSpa')
        .constant('APP_CONFIG', {
            'API_URL': 'https://localhost:8443/'
        })
        .constant('REFRESH_INTERVAL', 5000)
})();
