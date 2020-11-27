import {ActionTypes as NearestParamsActionTypes} from '../nearest/actions'

const defaultNearestParams = {
    range: 500,
    limit: 10
}

export function nearestParamsReducer(nearestParams = defaultNearestParams, action) {
    switch (action.type) {
        case NearestParamsActionTypes.paramsRefreshed:
            return action.nearestParams
        default:
            return nearestParams
    }
}