import {announcementsReducer} from './announcements'
import {stopReducer} from './stops'
import {nearestParamsReducer} from "./nearestParams";
import loadingReducer from "./loading";

export default function rootReducer(state = {}, action) {
    return {
        announcements: announcementsReducer(state.announcements, action),
        stop: stopReducer(state.stop, action),
        nearestParams: nearestParamsReducer(state.nearestParams, action),
        loading: loadingReducer(state.isLoading, action)
    }
}