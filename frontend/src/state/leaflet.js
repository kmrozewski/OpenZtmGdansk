import {ActionTypes as LeafletActionTypes} from '../spinner/actions'

export function leafletReducer(isLoading = false, action) {
    switch (action.type) {
        case LeafletActionTypes.refreshStarted:
            return true
        case LeafletActionTypes.refreshStopped:
            return false
        default:
            return isLoading
    }
}