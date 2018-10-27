(function() {
  'use strict';

  angular
      .module('ztmSpa')
      .service('SearchService', searchService);

  /** @ngInject */
  function searchService(Search) {

    this.searchByName = searchByName;

    function searchByName(stopName) {
      return Search.getByName({'stopName': stopName});
    }
  }

})();