(function() {
  'use strict';

  angular
    .module('ztmSpa')
    .config(config);

  /** @ngInject */
  function config($logProvider) {
    // Enable log
    $logProvider.debugEnabled(true);
  }

})();
