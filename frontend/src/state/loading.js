import {leafletReducer} from "./leaflet";
import {estimateReducer} from "./estimate";

export function loadingReducer(state = {}, action) {
    return {
        isLoadingMap: leafletReducer(state.isLoadingMap, action),
        isLoadingEstimate: estimateReducer(state.isLoadingEstimate, action)
    }
}