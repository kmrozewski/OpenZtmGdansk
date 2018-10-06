(function() {
  'use strict';

  angular
    .module('ztmSpa')
    .run(runBlock);

  /** @ngInject */
  function runBlock($log) {

    $log.debug('runBlock end');
  }

})();
