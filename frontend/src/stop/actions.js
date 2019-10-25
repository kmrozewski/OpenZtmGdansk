export const ActionTypes = {
	stopRefreshed: 'stopRefreshedAction'
}

export function stopsRefreshed(stop) {
	return {
		type: ActionTypes.stopRefreshed,
		stop
	}
}