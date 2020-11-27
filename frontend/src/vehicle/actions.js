export const ActionTypes = {
	vehiclesRefreshed: 'vehiclesRefreshedAction'
}

export function vehiclesRefreshed(vehicleLocation) {
	return {
		type: ActionTypes.vehiclesRefreshed,
		vehicleLocation
	}
}