import {announcementsReducer} from './announcements'
import {stopReducer} from './stops'

export default function rootReducer(state = {}, action) {
    return {
        announcements: announcementsReducer(state.announcements, action),
        stop: stopReducer(state.stop, action)
    }
}