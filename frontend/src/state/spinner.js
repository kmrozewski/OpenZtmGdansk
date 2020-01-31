import {ActionTypes as SpinnerActionTypes} from '../spinner/actions'

export function spinnerReducer(isLoading = false, action) {
    switch (action.type) {
        case SpinnerActionTypes.refreshStarted:
            return true
        case SpinnerActionTypes.refreshStopped:
            return false
        default:
            return isLoading
    }
}