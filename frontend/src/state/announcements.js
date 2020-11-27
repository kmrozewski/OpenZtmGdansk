import {ActionTypes as AnnouncementsActionTypes} from '../announcement/actions'

export function announcementsReducer(announcements = [], action) {
	switch (action.type) {
		case AnnouncementsActionTypes.announcementsRefreshed:
			return action.announcements
		default:
			return announcements
	}
}