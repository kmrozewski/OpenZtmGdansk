import {announcementsReducer} from './announcements'

export default function rootReducer(state = {}, action) {
    return {
        announcements: announcementsReducer(state.announcements, action)
    }
}