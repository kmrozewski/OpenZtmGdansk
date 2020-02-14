import {leafletReducer} from "./leaflet";
import {estimateReducer} from "./estimate";

export default function loadingReducer(state = {}, action) {
    return {
        isLoadingMap: leafletReducer(state.isLoadingMap, action),
        isLoadingEstimate: estimateReducer(state.isLoadingEstimate, action)
    }
}