export const ActionTypes = {
	stopRefreshed: 'stopRefreshedAction'
}

export function stopsRefreshed(stop) {
	return {
		type: ActionTypes.stopRefreshed,
		stop
	}
}

export function stopNamesRefreshed(names) {
	return {
		type: ActionTypes.stopRefreshed,
		names
	}
}