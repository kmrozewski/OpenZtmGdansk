(function() {
  'use strict';

  angular
    .module('ztmSpa')
    .config(routerConfig);

  /** @ngInject */
  function routerConfig($stateProvider, $urlRouterProvider) {
    $stateProvider
      .state('home', {
        url: '/',
        templateUrl: 'app/main/main.html',
        controller: 'MainController',
        controllerAs: 'main'
      })
      .state('search', {
        url: '/search/{stopName}',
        templateUrl: 'app/search/search.html',
        controller: 'SearchController',
        params: {
          'stopName': null
        }
      })
      .state('nearest', {
        url: '/nearest',
        templateUrl: 'app/nearest/nearest.html',
        controller: 'NearestController'
      })
      .state('about', {
        url: '/about',
        templateUrl: 'app/about/about.html'
      });

      $urlRouterProvider.otherwise('/');
  }

})();
