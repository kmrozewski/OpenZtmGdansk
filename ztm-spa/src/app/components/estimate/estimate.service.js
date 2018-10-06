(function() {
  'use strict';

  angular
      .module('ztmSpa')
      .service('EstimateService', estimateService);

  /** @ngInject */
  function estimateService(Estimate) {

    this.getEstimate = getEstimate;

    function getEstimate(stopIds) {
      return Estimate.post(stopIds);
    }
  }

})();