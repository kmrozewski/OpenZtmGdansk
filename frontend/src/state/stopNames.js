import {ActionTypes as stopActionTypes} from '../stop/actions'

export function stopNamesReducer(names = [], action) {
    switch (action.type) {
        case stopActionTypes.stopRefreshed:
            return action.names
        default:
            return names
    }
}