(function() {
  'use strict';

  angular
      .module('ztmSpa')
      .service('AnnouncementService', announcementService);

  /** @ngInject */
  function announcementService(Announcement) {

    this.getAnnouncement = getAnnouncement;

    function getAnnouncement(stopIds) {
      return Announcement.post(stopIds);
    }
  }

})();