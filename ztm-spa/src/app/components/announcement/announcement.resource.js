(function() {
    'use strict';

    angular
        .module('ztmSpa')
        .service('Announcement', announcement);

    /** @ngInject */
    function announcement($resource, APP_CONFIG) {
        return $resource(APP_CONFIG.API_URL + 'announcement/announcement', {}, {
            post: {
                method: 'POST',
                isArray: true
            }
        });
    }

})();