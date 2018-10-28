(function() {
  'use strict';

  angular
      .module('ztmSpa')
      .service('RouteService', routeService);

  /** @ngInject */
  function routeService(Route) {

    this.getById = getById;

    function getById(routeId) {
      return Route.getById({routeId: routeId});
    }
  }

})();