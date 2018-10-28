(function() {
  'use strict';

  angular
      .module('ztmSpa')
      .service('AnnouncementService', announcementService);

  /** @ngInject */
  function announcementService(Announcement) {

    this.getAnnouncements = getAnnouncements;

    function getAnnouncements() {
      return Announcement.getAll();
    }
  }

})();