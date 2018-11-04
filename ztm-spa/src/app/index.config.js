(function() {
  'use strict';

  angular
    .module('ztmSpa')
    .config(config);

  /** @ngInject */
  function config($logProvider, $qProvider) {
    // Enable log
    $logProvider.debugEnabled(true);
    $qProvider.errorOnUnhandledRejections(false);
  }

})();
