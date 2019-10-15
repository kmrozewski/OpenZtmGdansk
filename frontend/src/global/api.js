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
        return []
    }
}

export const getRouteById = async (routeId) => {
    try {
        const result = await axios.get(API + 'route/' + routeId)
        return result.data
    } catch (error) {
        return {}
    }
}