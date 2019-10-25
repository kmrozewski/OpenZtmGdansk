import axios from 'axios'
import {API} from './const'

export const getDelays = async (stopId) => {
	try {
        const result = await axios.get(API + "estimate/" + stopId)

        return {
            isLoading: false,
            delays   : result.data.delay
        }
    } catch (error) {
        console.log('Error', error)
        return {
            isLoading: false,
            delays   : []
        }
    }
}

export const getDelaysAggregated = async (stopIds) => {
    try {
        const result = await axios.post(API + "estimate", stopIds)

        return {
            isLoading: false,
            delays: result.data.delay
        }
    } catch (error) {
        console.log('Error', error)
        return {
            isLoading: false,
            delays: []
        }
    }
}

export const getAnnouncements = async () => {
    try {
        const result = await axios.get(API + 'announcement/all')
        return result.data
    } catch (error) {
        console.log('Error', error)
        return []
    }
}

export const getRouteById = async (routeId) => {
    try {
        const result = await axios.get(API + 'route/' + routeId)
        return result.data
    } catch (error) {
        console.log('Error', error)
        return {}
    }
}

export const getStopByName = async (stopName) => {
    try {
        const result = await axios.get(API + 'search/' + stopName)
        return result.data
    } catch (error) {
        console.log('Error', error)
        return {
            name: stopName,
            codes: []
        }
    }
}

const getRequestTripId = (routeId, tripId) => 'R' + routeId + 'T' + tripId

export const getTripById = async (routeId, tripId) => {
    try {
        const requestTripId = getRequestTripId(routeId, tripId)
        const result = await axios.get(API + 'trip/' + requestTripId)

        return result.data
    } catch (error) {
        console.log('Error', error)
        return {}
    }
}