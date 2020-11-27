export const ActionTypes = {
    announcementsRefreshed: 'AnnouncementsRefreshedAction'
}

export function announcementsRefreshed(announcements) {
	return {
		type: ActionTypes.announcementsRefreshed,
		announcements
	}
}