export const ActionTypes = {
    refreshStarted: 'LeafletMapRefreshStartedAction',
    refreshStopped: 'LeafletMapRefreshStoppedAction'
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