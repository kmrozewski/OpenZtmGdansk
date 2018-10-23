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
        url: '/search/{stopId:int}', //TODO: use state parameters
        templateUrl: 'app/search/search.html',
        controller: 'SearchController'
      })
      .state('about', {
        url: '/about',
        templateUrl: 'app/about/about.html'
      });

      $urlRouterProvider.otherwise('/');
  }

})();
