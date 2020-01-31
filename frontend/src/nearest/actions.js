export const ActionTypes = {
    paramsRefreshed: 'paramsRefreshedAction'
}

export function paramsRefreshed(nearestParams) {
    return {
        type: ActionTypes.paramsRefreshed,
        nearestParams: nearestParams
    }
}