export const ActionTypes = {
    refreshStarted: 'SpinnerRefreshStartedAction',
    refreshStopped: 'SpinnerRefreshStoppedAction'
}

export function refreshStarted() {
    console.log('refreshStarted')
    return {
        type: ActionTypes.refreshStarted
    }
}

export function refreshStopped() {
    console.log('refreshStopped')
    return {
        type: ActionTypes.refreshStopped
    }
}