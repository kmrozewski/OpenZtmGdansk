import {ActionTypes as vehicleActionTypes} from '../vehicle/actions'

const defaultVehicleLocation = {
    lastUpdate: new Date(),
    vehicles: {}
}

export function vehicleReducer(vehicleLocation = defaultVehicleLocation, action) {
    switch (action.type) {
        case vehicleActionTypes.vehiclesRefreshed:
            return action.vehicleLocation
        default:
            return vehicleLocation
    }
}