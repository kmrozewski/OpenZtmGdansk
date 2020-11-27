import {ActionTypes as EstimateActionTypes} from '../delay/actions'

export function estimateReducer(isLoading = false, action) {
    switch (action.type) {
        case EstimateActionTypes.refreshStarted:
            return true
        case EstimateActionTypes.refreshStopped:
            return false
        default:
            return isLoading
    }
}