(function() {
    'use strict';

    angular
        .module('ztmSpa')
        .service('SearchService', searchService);

    /** @ngInject */
    function searchService(Search) {

        this.searchByName = searchByName;
        this.findNearest = findNearest;

        function searchByName(stopName) {
            return Search.getByName({
                'stopName': stopName
            });
        }

        function findNearest(request) {
            return Search.findNearest(request);
        }
    }

})();