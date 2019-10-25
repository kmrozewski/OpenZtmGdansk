import {ActionTypes as stopActionTypes} from '../stop/actions'

export function stopReducer(stop = {}, action) {
    switch (action.type) {
        case stopActionTypes.stopRefreshed:
            return action.stop
        default:
            return stop
    }
}