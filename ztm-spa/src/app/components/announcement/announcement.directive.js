(function() {
    'use strict';

    angular
        .module('ztmSpa')
        .directive('announcement', function(AnnouncementService) {
            return {
                restrict: 'E',
                templateUrl: 'app/components/announcement/announcement.html',
                scope: {
                    messages: '='
                },
                link: function(scope) {
                    AnnouncementService.getAnnouncements().$promise.then(function(response) {
                        console.log(response);
                        if (response.length >= 0) {
                            scope.messages = response;
                        }
                    })
                }
            };
        })
})();