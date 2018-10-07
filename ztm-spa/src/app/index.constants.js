(function() {
    'use strict';

    angular
        .module('ztmSpa')
        .constant('APP_CONFIG', {
            'API_URL': 'http://localhost:8080/'
        })
        .constant('REFRESH_INTERVAL', 5000)
})();
